/**
 * Group number: 10
 * Shayyan Asim (30149567),
 * Angelo Jerome T. Reynante (30160763),
 * Masroor Posh (30156171),
 * Mohamad Hussein (30145507)
 * */
package edu.ucalgary.oop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

public class Schedule {
    /* TreeMap<time period, list of treatments> */
    private TreeMap<Integer, ArrayList<Treatment>> treatmentTasks;

    /* HashMap <species, list of feeding tasks> */
    private HashMap<String, ArrayList<Task>> feedingTasks;

    /* This is the entire list of cage cleaning tasks */
    private ArrayList<CageTask> cageTasks;

    /* This is the entire schedule of the day */
    private HashMap<Integer, Timeslot> schedule;

    private LocalDate date;
    private String scheduleResult;
    private int day;
    private int month;
    private int year;

    private Connection dbConnection;
    private final String URL = "jdbc:mysql://localhost/EWR";
    private final String USERNAME = "oop";
    private final String PASSWORD = "password";

    private ArrayList<Treatment> invalidTreatments;
    private int invalidTreatmentsIndex;

    /**
     *
     * @param day
     * @param month
     * @param year
     * @throws IllegalArgumentException
     */
    public Schedule(int day, int month, int year) throws IllegalArgumentException {
        if (day < 0) {
            throw new IllegalArgumentException("Day cannot be negative");
        }
        if (month < 0 || month > 12) {
            throw new IllegalArgumentException("Month cannot be negative");
        }
        if (year < 0) {
            throw new IllegalArgumentException("Year cannot be negative");
        }
        this.day = day;
        this.month = month;
        this.year = year;
        this.date = LocalDate.of(year, month, day);
        this.treatmentTasks = new TreeMap<Integer, ArrayList<Treatment>>();
        this.cageTasks = new ArrayList<CageTask>();
        this.scheduleResult = "";

        this.feedingTasks = new HashMap<String, ArrayList<Task>>();
        for (AnimalInfo animal : AnimalInfo.values()) {
            feedingTasks.put(animal.name().toLowerCase(), new ArrayList<Task>());
        }

        this.schedule = new HashMap<Integer, Timeslot>();
        for (int i = 0; i < 24; i++) {
            schedule.put(i, new Timeslot());
        }
    }

    /*
     * This method reads the database and instantiates the tasks and animals
     * and puts them into the appropriate arraylists and hashmaps
     */

    /**
     *
     * @throws SQLException
     */
    public void readSql() throws SQLException {
        try {
            dbConnection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException();
        }
        instantiateTasks();
        dbConnection.close();

    }

    /**
     * This method instantiates the tasks from the database and puts them into the
     * treatmentTasks and normalTasks arraylists
     */
    private void instantiateTasks() {
        ArrayList<Animal> animals = new ArrayList<Animal>();
        ArrayList<MedicalTask> medicalTasks = new ArrayList<MedicalTask>();

        try {
            // Getting the animals from the database and putting them into an arraylist
            Statement statement = dbConnection.createStatement();
            ResultSet queryResult = statement.executeQuery("SELECT * FROM ANIMALS");
            while (queryResult.next()) {

                int animalID = queryResult.getInt("AnimalID");
                String animalNickname = queryResult.getString("AnimalNickname");
                String animalSpecies = queryResult.getString("AnimalSpecies");

                String activityPattern = AnimalInfo.getActivityPattern(animalSpecies);
                Animal animalObj = new Animal(animalID, animalSpecies, animalNickname, activityPattern);
                animals.add(animalObj);
            }

            // Getting the medical tasks from the database and putting them into an
            // arraylist
            String nickname = "";
            queryResult = statement.executeQuery("SELECT * FROM TASKS");
            while (queryResult.next()) {

                int taskID = queryResult.getInt("TaskID");
                String taskDescription = queryResult.getString("Description");
                int taskDuration = queryResult.getInt("Duration");
                int taskMaxWindow = queryResult.getInt("MaxWindow");

                /* This is done to find the animal nickname and add it to the medical task */
                for (Animal iterAnimal : animals) {
                    if (iterAnimal.getAnimalID() == taskID) {
                        nickname = iterAnimal.getAnimalNickname();
                        break;
                    }
                }
                MedicalTask medicalTaskObj = new MedicalTask(taskDescription, taskDuration, taskMaxWindow, taskID,
                        nickname);
                medicalTasks.add(medicalTaskObj);
            }

            /*
             * Getting the medical tasks from the database and connecting animals and tasks
             * to add to treatmentTasks arraylist
             */
            queryResult = statement.executeQuery("SELECT * FROM TREATMENTS");
            while (queryResult.next()) {

                int animalID = queryResult.getInt("AnimalID");
                int taskID = queryResult.getInt("TaskID");
                int taskStartHour = queryResult.getInt("StartHour");

                // to find the animal object
                Animal treatmentAnimal = null; // this is a pointer to the animal object?
                for (Animal iterAnimal : animals) {
                    if (iterAnimal.getAnimalID() == animalID) {
                        treatmentAnimal = iterAnimal;
                        break;
                    }
                }

                // to find the medical task object
                MedicalTask treatmentTask = null;
                for (MedicalTask iterMedicalTask : medicalTasks) {
                    if (iterMedicalTask.getTaskID() == taskID) {
                        treatmentTask = iterMedicalTask;
                        break;
                    }
                }

                if (treatmentAnimal == null || treatmentTask == null) {
                    throw new Exception("Animal or medical task not found");
                }

                Treatment treatment = new Treatment(treatmentTask, treatmentAnimal, taskStartHour);
                int treatmentStartHour = treatment.getStartHour();
                if (treatmentTasks.containsKey(treatmentStartHour)) {
                    treatmentTasks.get(treatmentStartHour).add(treatment);
                } else {
                    ArrayList<Treatment> newTreatmentList = new ArrayList<Treatment>();
                    newTreatmentList.add(treatment);
                    treatmentTasks.put(treatmentStartHour, newTreatmentList);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();

        } catch (Exception generalException) {
            generalException.printStackTrace();
        }

        /* Finding the taskID of orphans and using hashset for only unique values */
        Set<Integer> orphansTaskIDs = new HashSet<Integer>();
        for (ArrayList<Treatment> iTreatmentList : treatmentTasks.values()) {
            for (Treatment iTreatment : iTreatmentList) {
                if (iTreatment.getMedTask().getTaskID() == 1) {
                    orphansTaskIDs.add(iTreatment.getTreatedAnimal().getAnimalID());
                }
            }
        }

        /*
         * Making a cage cleaning task (NormalTask) and FeedingTask
         * for all existing animal and feeding animals as well
         */

        for (Animal iterAnimal : animals) {
            String animalSpecies = iterAnimal.getAnimalSpecies();
            String activityPattern = iterAnimal.getActivityPatternType();

            // Adding a cage cleaning task for each animal
            int duration = AnimalInfo.getCleanCageDuration(animalSpecies);
            CageTask cageCleaning = null;
            try {
                cageCleaning = new CageTask("Cage Cleaning", duration, 0,
                        iterAnimal.getAnimalNickname()); // -1 means no max window

                this.cageTasks.add(cageCleaning);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }

            /*
             * Adding a feeding task for each animal that is not a kit
             * (feeding kits is already in medical task)
             */
            if (orphansTaskIDs.contains(iterAnimal.getAnimalID())) {
                continue;
            }

            /* Adding a feeding task for each animal */
            FeedingTask feeding = null;
            int maxWindow = -1;
            int feedingDuration = AnimalInfo.getFeedingPrepDuration(animalSpecies);
            feedingDuration += AnimalInfo.getFeedingDuration(animalSpecies);

            /* Ensuring the right max window is set for the feeding task */
            if (activityPattern.equalsIgnoreCase("nocturnal")) {
                maxWindow = 3;
            } else if (activityPattern.equalsIgnoreCase("diurnal")) {
                maxWindow = 11;
            } else if (activityPattern.equalsIgnoreCase("crepuscular")) {
                maxWindow = 22;
            }

            try {
                feeding = new FeedingTask("Feeding", feedingDuration,
                        maxWindow, iterAnimal.getAnimalNickname(), iterAnimal.getAnimalSpecies());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }

            /* This adds the task to the associated animal */
            this.feedingTasks.get(iterAnimal.getAnimalSpecies()).add(feeding);

        }

    }

    /**
     * These methods makes the most efficient schedule and stores it in a hashmap
     * Adds the medical tasks first, then the feeding tasks and then the cage
     * cleaning
     * tasks
     * If there are too many medical tasks to fit in two hours, it throws an
     * exception
     * 
     * @throws IllegalTaskException
     *                              nextTime is the time of the next timeslot when
     *                              iterating
     *                              timeCounter is the number of timeslots that have
     *                              been iterated through
     * 
     */
    public void makeEfficientSchedule() throws IllegalTaskException, ImpossibleScheduleException {
        for (int startHour : treatmentTasks.keySet()) {
            ArrayList<Treatment> treatments = treatmentTasks.get(startHour);
            int nextTime = startHour;
            int iCurrentTreat = 0;
            int timeCounter = 0;
            Timeslot timeslot = schedule.get(startHour);
            Timeslot nextTimeslot = schedule.get(nextTime);

            /* Sorting the treatments by max window */
            Collections.sort(treatments, new Comparator<Treatment>() {
                @Override
                public int compare(Treatment t1, Treatment t2) {
                    int maxWindow1 = t1.getMedTask().getMaxWindow();
                    int maxWindow2 = t2.getMedTask().getMaxWindow();
                    return Integer.compare(maxWindow1, maxWindow2);
                }
            });

            for (Treatment iterTreatment : treatments) {
                // TODO: change to nextTimeslot if you want to add the task in the timeslot it
                // starts at
                int duration = iterTreatment.getMedTask().getDuration();
                int currMaxWindow = iterTreatment.getMedTask().getMaxWindow();

                int timeDifference = nextTimeslot.getTimeAvailable() - duration;

                if (timeDifference < 0) {

                    /* This is to check if the task is over the max window */
                    if (timeCounter + 1 >= currMaxWindow) {
                        String htmlError = "<html><body style='text-align:center'>" +
                                "<br>" +
                                "The medical task " + iterTreatment.getMedTask().getDescription() +
                                " on " + iterTreatment.getMedTask().getAnimalNickname() +
                                " <br> cannot be scheduled at " + startHour +
                                ":00 because it violates<br> the maximum window of " + currMaxWindow +
                                " hour(s).<br><br>Please request a change of requirements." +
                                "</body></html>";
                        throw new ImpossibleScheduleException(htmlError);
                    }
                    if (timeCounter == 1) {
                        this.invalidTreatments = treatments;
                        this.invalidTreatmentsIndex = iCurrentTreat;
                        clearSchedule();
                        throw new IllegalTaskException("There are too many medical tasks to fit between "
                                + (startHour) + ":00 " + (startHour + timeCounter + 1)
                                + ":00. Please move some tasks.");
                    }

                    else {
                        timeCounter++;
                    }

                    timeslot.addTask(iterTreatment.getMedTask());

                    nextTimeslot.setTimeAvailable(0);
                    nextTimeslot.setBackupNeeded(true);

                    nextTime++;
                    nextTimeslot = schedule.get(nextTime);
                    try {
                        nextTimeslot.addTimeAvailable(timeDifference);
                    } catch (IllegalArgumentException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    try {
                        timeslot.addTask(iterTreatment.getMedTask());
                        nextTimeslot.addTimeAvailable(-duration);
                    } catch (IllegalArgumentException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
                iCurrentTreat++;
            }
        }

        addNormalTasks();

    }

    /**
     * This method helps makeEfficientSchedule add the normal tasks to the schedule
     * 
     * @throws ImpossibleScheduleException if the tasks cannot be added to the
     *                                     schedule
     */
    private void addNormalTasks() throws ImpossibleScheduleException {
        for (String animal : this.feedingTasks.keySet()) {
            ArrayList<Task> feedingTasksList = this.feedingTasks.get(animal);
            if (feedingTasksList.isEmpty()) {
                continue;
            }

            int maxWindow = feedingTasksList.get(0).getMaxWindow();
            /* 3 because there is a 3 hour window to all feeding tasks */
            int startHour = maxWindow - 3;
            Timeslot timeslot = schedule.get(startHour);

            int timeRemaining = timeslot.getTimeAvailable();
            int feedingDuration = AnimalInfo.getFeedingDuration(animal);
            int prepDuration = AnimalInfo.getFeedingPrepDuration(animal);
            int minTimeFeeding = prepDuration + feedingDuration;

            while (startHour < maxWindow) {
                if (timeRemaining >= minTimeFeeding) {
                    int tasksToGroup = (timeRemaining - prepDuration) / feedingDuration;
                    timeRemaining -= (prepDuration);
                    for (int i = 0; i < tasksToGroup; i++) {
                        timeslot.addTask(feedingTasksList.get(0));
                        feedingTasksList.remove(0);
                        timeRemaining -= feedingDuration;
                        if (feedingTasksList.isEmpty()) {
                            break;
                        }
                    }
                    timeslot.setTimeAvailable(timeRemaining);
                    if (feedingTasksList.isEmpty()) {
                        break;
                    }
                }

                startHour++;
                if (startHour == maxWindow) {
                    throw new ImpossibleScheduleException("There isn't room for all the feeding tasks");
                }
                timeslot = schedule.get(startHour);
                timeRemaining = timeslot.getTimeAvailable();
            }
        }
        /*
         * These lines add cage tasks to the schedule
         * No exceptions are thrown when they cannot fit in the schedule
         * because cage tasks are not necessary to be included based on the handout
         * "and with cage cleaning we do it once a day whenever there's room in the schedule."
         */
        int startHour = 0;
        for (CageTask cageTask : this.cageTasks) {
            // int timeTaken = cageTask
            while (startHour < 24) {

                Timeslot timeslot = schedule.get(startHour);
                if (timeslot.getTimeAvailable() >= cageTask.getDuration()) {
                    timeslot.addTask(cageTask);
                    try {
                        timeslot.addTimeAvailable(-cageTask.getDuration());
                    } catch (IllegalArgumentException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
                }
                startHour++;
            }
        }
    }

    /**
     * This method calculates the most efficient schedule in string form and stores
     * the string result inside of private variable scheduleResult
     */
    /**
     * Calculate the efficient schedule.
     */
    public String calculateEfficient() {
        String result = "Schedule for " + this.date.toString() + "\n\n";

        for (int startHour : schedule.keySet()) {
            Timeslot timeslot = schedule.get(startHour);

            if (timeslot.getTasks().isEmpty()) {
                continue;
            }

            result += startHour + ":00";

            // Adding Backup volunteer tag
            if (timeslot.getBackupNeeded()) {
                result += " [+ backup volunteer]\n";
            } else {
                result += "\n";
            }

            /* Adding tasks */
            result += tasksFormat(timeslot);

            result += "\n";

        }
        return this.scheduleResult = result;

    }

    /**
     * This method formats the tasks in a timeslot into a string
     * 
     * @param timeslot
     * @return String of tasks in the timeslot
     */
    private String tasksFormat(Timeslot timeslot) {
        HashMap<String, ArrayList<Object>> feedingCount = new HashMap<String, ArrayList<Object>>();
        String result = "";
        String cageResult = "";

        for (Task task : timeslot.getTasks()) {

            /* Storing feeding animals count and nicknames */
            if (task instanceof FeedingTask) {
                String species = ((FeedingTask) task).getSpecies();

                if (feedingCount.containsKey(species)) {
                    ArrayList<Object> feedingInfo = feedingCount.get(((FeedingTask) task).getSpecies());
                    int count = (Integer) feedingInfo.get(0); // get the current count
                    count++; // increment the count by 1
                    feedingInfo.set(0, count); // update the count in the ArrayList
                    String nicknames = (String) feedingInfo.get(1);
                    feedingInfo.remove(1);
                    feedingInfo.add(nicknames + ", " + task.getAnimalNickname());
                    feedingCount.put(species, feedingInfo);
                } else {
                    ArrayList<Object> feedingInfo = new ArrayList<Object>();
                    feedingInfo.add(1);
                    feedingInfo.add(task.getAnimalNickname());
                    feedingCount.put(species, feedingInfo);
                }

            } else if (task instanceof MedicalTask) {
                /* Adding medical tasks */
                result += "* " + task.getDescription() + " (" + task.getAnimalNickname() + ")\n";
            } else if (task instanceof CageTask) {
                /* Adding cage cleaning tasks */
                cageResult += "* " + task.getDescription() + " (" + task.getAnimalNickname() + ")\n";
            } else {
                System.out.println("Error: Unknown task type");
            }
        }

        /* Adding feeding animals */
        for (String animalSpecies : feedingCount.keySet()) {
            ArrayList<Object> feedingInfo = feedingCount.get(animalSpecies);
            int count = (Integer) feedingInfo.get(0);
            String nicknames = (String) feedingInfo.get(1);
            result += "* Feeding - " + animalSpecies + " (" + count + ": " + nicknames + ")\n";
        }

        result += cageResult;
        return result;
    }

    /**
     * This method moves a treatment to a new start hour
     * and removes the old one
     * 
     * @param treatment
     * @param startHour
     */
    public void moveTreatment(Treatment treatment, int startHour) throws SQLException {
        int oldStartHour = treatment.getStartHour();
        treatmentTasks.get(oldStartHour).remove(treatment);

        treatment.setStartHour(startHour);

        if (!treatmentTasks.containsKey(startHour)) {
            treatmentTasks.put(startHour, new ArrayList<Treatment>());
        }
        treatmentTasks.get(startHour).add(treatment);
        try {
            dbConnection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String query = "UPDATE TREATMENTS SET startHour = ? WHERE TaskID = ? AND AnimalID = ? AND StartHour = ?";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setInt(1, startHour);
            statement.setInt(2, treatment.getMedTask().getTaskID());
            statement.setInt(3, treatment.getTreatedAnimal().getAnimalID());
            statement.setInt(4, oldStartHour);
            int rowsUpdated = statement.executeUpdate();
            dbConnection.close();
        } catch (SQLException e) {
            throw new SQLException();
        }

    }

    /**
     * Adds task.
     * 
     * @param task
     * @param startHour
     */
    public void addTask(Task task, int startHour) {
        schedule.get(startHour).addTask(task);
    }

    /**
     * This method adds a treatment to the
     * database and the treatmentTasks map.
     * 
     * @param treatment      The selected treatment to move
     * @param startHour      The new start hour
     * @param changeDatabase Whether or not to change the database
     */
    public void addTreatment(Treatment treatment, int startHour, boolean changeDatabase) {
        int animalID = treatment.getTreatedAnimal().getAnimalID();
        int taskID = treatment.getMedTask().getTaskID();
        if (changeDatabase) {
            try {
                dbConnection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                String query = "INSERT INTO TREATMENTS (AnimalID, TaskID, StartHour) VALUES (?, ?, ?);";
                PreparedStatement statement = dbConnection.prepareStatement(query);
                statement.setInt(1, animalID);
                statement.setInt(2, taskID);
                statement.setInt(3, startHour);
                int rowsUpdated = statement.executeUpdate();
                dbConnection.close();

            } catch (SQLException e) {
                System.out.println("Could not add the treatment to the database");
                e.printStackTrace();
            }
        }

        if (!treatmentTasks.containsKey(startHour)) {
            treatmentTasks.put(startHour, new ArrayList<Treatment>());
        }
        treatmentTasks.get(startHour).add(treatment);

    }

    /**
     * clears the schedule.
     */
    public void clearSchedule() {
        this.schedule = new HashMap<Integer, Timeslot>();
        for (int i = 0; i < 24; i++) {
            schedule.put(i, new Timeslot());
        }
    }

    /* GETTERS */

    /**
     * gets invalid treatments, if there are none returns null.
     * 
     * @return invalid treatments
     */
    public ArrayList<Treatment> getInvalidTreatments() {
        return invalidTreatments;
    }

    /**
     * gets invalid treatments index.
     * 
     * @return invalid treatments index
     */
    public int getInvalidTreatmentsIndex() {
        return invalidTreatmentsIndex;
    }

    /**
     *
     * @return schedule result
     */
    public String getScheduleResult() {
        return this.scheduleResult;
    }

    /**
     *
     * @return schedule
     */
    public HashMap<Integer, Timeslot> getSchedule(){return this.schedule;}

    /**
     *
     * @param schedule
     */
    public void setSchedule(HashMap<Integer, Timeslot> schedule) {
        this.schedule = schedule;
    }

    public TreeMap<Integer, ArrayList<Treatment>> getTreatmentTasks() {
        return this.treatmentTasks;
    }

    /**
     *
     * @param treatmentTasks
     */
    public void setTreatmentTasks(TreeMap<Integer, ArrayList<Treatment>> treatmentTasks) {
        this.treatmentTasks = treatmentTasks;
    }

    /**
     *
     * @return day
     */
    public int getDay() {
        return this.day;
    }

    /**
     *
     * @param day
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     *
     * @return month
     */
    public int getMonth() {
        return this.month;
    }

    /**
     *
     * @param month
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     *
     * @return year
     */
    public int getYear() {
        return this.year;
    }

    /**
     *
     * @param year
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     *
     * @return allTreatment
     */
    public ArrayList<Treatment> getTreatmentList() {
        ArrayList<Treatment> allTreatments = new ArrayList<>();
        for (ArrayList<Treatment> iterTreatmentList : treatmentTasks.values()) {
            for (Treatment iterTreatment : iterTreatmentList) {
                allTreatments.add(iterTreatment);
            }
        }
        return allTreatments;
    }

    /**
     *
     * @return date
     */
    public LocalDate getDate() {
        return this.date;
    }

    /**
     *
     * @param date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }
}

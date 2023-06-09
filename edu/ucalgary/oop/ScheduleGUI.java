/**
 * Group number: 10
 * Shayyan Asim (30149567),
 * Angelo Jerome T. Reynante (30160763),
 * Masroor Posh (30156171),
 * Mohamad Hussein (30145507)
 * */
package edu.ucalgary.oop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class ScheduleGUI extends JFrame implements ActionListener {
    private JLabel titleLabel, dateLabel, startTimeLabel, taskLabel;
    private JTextField dateField, startTimeField, taskField;
    private JButton calculateButton;
    private JTextArea scheduleArea;
    private JComboBox<String> taskComboBox;
    private Schedule schedule;
    private final String SQL_EXCEPTION_MESSAGE = "<html>" +
            "<div style='text-align:center'>" +
            "<h2>Error</h2>" +
            "<p>Could not make a connection to the database.</p>" +
            "<p>Please contact your system administrator.</p>" +
            "</div></html>";

    /**
     * Constructor
     */
    public ScheduleGUI(Schedule schedule) {
        this();
        this.schedule = schedule;
    }

    public ScheduleGUI() {
        // Set the properties of the JFrame
        setTitle("Schedule Calculator");
        setSize(1000, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the components
        titleLabel = new JLabel("Schedule Calculator", JLabel.CENTER);

        dateLabel = new JLabel("Date (YYYY-MM-DD):");
        dateField = new JTextField(10);

        calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(this);

        // Set the alignment of the button
        calculateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        calculateButton.setAlignmentY(Component.CENTER_ALIGNMENT);

        // Set the font size of the button
        Font buttonFont = calculateButton.getFont();
        calculateButton.setFont(new Font(buttonFont.getName(), buttonFont.getStyle(), 16)); // Change the size (16) to
                                                                                            // the desired size

        scheduleArea = new JTextArea(20, 40);
        scheduleArea.setEditable(false);

        // Add the components to the JFrame
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(titleLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        inputPanel.add(dateLabel);
        inputPanel.add(dateField);

        inputPanel.add(new JLabel(""));
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(calculateButton);
        inputPanel.add(new JLabel(""));
        inputPanel.add(new JLabel(""));
        add(inputPanel, BorderLayout.NORTH);

        add(buttonPanel, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(scheduleArea);
        add(scrollPane, BorderLayout.CENTER);

        // Display the JFrame
        setVisible(true);

    }

    /**
     *
     * @param e the event to be processed
     */
    public void actionPerformed(ActionEvent e) {
        try {
            // Get the values from the input fields
            String date = dateField.getText();
            // String startTime = startTimeField.getText();
            // String taskType = (String) taskComboBox.getSelectedItem();

            // Calculate the schedule
            String schedule = calculateSchedule(date, rootPaneCheckingEnabled);

            // Display the schedule in the text area
            scheduleArea.setText(schedule);

        } catch (IllegalArgumentException ex) {
            // Display an error message if the date format is incorrect
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Generates the schedule
     * 
     * @param schedule
     */
    private void generateFile(String schedule) {
        String fileName = "schedule_" + System.currentTimeMillis() + ".txt";
        try (PrintWriter writer = new PrintWriter(new File(fileName))) {
            writer.write(schedule);
            JOptionPane.showMessageDialog(this, "Schedule saved", "File Saved", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error saving file: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Calculates the schedule based on the inputs
     * 
     * @param dateStr
     * @param backupVolunteerNeeded
     * @return
     * @throws IllegalArgumentException
     */
    private String calculateSchedule(String dateStr, boolean backupVolunteerNeeded) throws IllegalArgumentException {
        // Check the date format
        try {
            LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please enter a date in the format: yyyy-MM-dd");
        }

        StringBuilder sb = new StringBuilder();
        if (this.schedule == null) {
            Schedule tempSchedule = new Schedule(
                    Integer.parseInt(dateStr.substring(8)),
                    Integer.parseInt(dateStr.substring(5, 7)),
                    Integer.parseInt(dateStr.substring(0, 4)));

            try {
                tempSchedule.readSql();
            } catch (SQLException e) {
                JLabel impSchedException = new JLabel(this.SQL_EXCEPTION_MESSAGE);
                impSchedException.setPreferredSize(new Dimension(360, 100));
                JOptionPane.showMessageDialog(null, impSchedException,
                        "Database Error", JOptionPane.ERROR_MESSAGE);
            }
            this.schedule = tempSchedule;
        }

        while (true) {

            try {
                schedule.makeEfficientSchedule();
                break;
            } catch (IllegalTaskException e) {
                /*
                 * THIS IS WHEN THERE IS AN ILLEGAL AMOUNT OF TASKS
                 * MADE IT SO IT ITERATES THROUGH THE INVALID TASKS
                 * AND ASKS THE USER TO CHOOSE A NEW START TIME
                 * MAKE SURE TO DISPLAY THE INVALID TASK TO THE USER
                 * AND MAKE SURE TO GET THE NEW START TIME FROM THE USER
                 */

                JLabel impSchedException = new JLabel(e.getMessage());
                JOptionPane.showMessageDialog(null, impSchedException, "Error", JOptionPane.ERROR_MESSAGE);

                ArrayList<Treatment> invalidTreatments = schedule.getInvalidTreatments();
                int invalidIndex = schedule.getInvalidTreatmentsIndex();

                for (int index = invalidIndex; index < invalidTreatments.size(); index++) {
                    int maxWindow = invalidTreatments.get(index).getMedTask().getMaxWindow();
                    Treatment invalidTreat = invalidTreatments.get(index);
                    // TODO: display to the user the invalid task
                    // TODO: ask the user to choose a new start time

                    String medTaskDescription = invalidTreat.getMedTask().getDescription();
                    String medTaskAnimal = invalidTreat.getMedTask().getAnimalNickname();
                    String illegalTaskMessage = "<html>" +
                            "<center><font size='6'><b>SCHEDULE IMPOSSIBLE</b></font></center>" +
                            "<center><font size='6'><b>PLEASE ENTER A TIME TO MOVE THIS TASK</b></font></center>" +
                            "<table>" +
                            "<tr><td colspan='2'>&nbsp;</td></tr>" +
                            "<tr><td><b>Treatment:</b></td><td>" + medTaskDescription + "</td></tr>" +
                            "<tr><td><b>Treated Animal:</b></td><td>" + medTaskAnimal + "</td></tr>" +
                            "<tr><td><b>Invalid start time:</b></td><td>" + invalidTreat.getStartHour() + "</td></tr>" +
                            "<tr><td><b>Max window:</b></td><td>" + maxWindow + "</td></tr>" +
                            "<tr><td colspan='2'>&nbsp;</td></tr>" +
                            "</table>" +
                            "<br>" +
                            "Type the new start hour in the box below (HH:00)." +
                            "</html>";

                    JFrame frame = new JFrame("");
                    JLabel text = new JLabel(illegalTaskMessage);
                    UIManager.put("OptionPane.minimumSize", new Dimension(500, 200));
                    String input = JOptionPane.showInputDialog(frame, text);

                    try {
                        LocalTime.parse(input, DateTimeFormatter.ofPattern("HH:mm"));
                        if (!input.substring(3).equals("00")) {
                            throw new IllegalArgumentException(
                                    "Invalid minute format. Please enter a time in the format: HH:00");
                        }
                    } catch (DateTimeParseException f) {
                        throw new IllegalArgumentException(
                                "Invalid start time format. Please enter a time in the format: HH:00");
                    } catch (Exception f) {
                        /* Skip for the case of no input */
                    }
                    int startHourChosen = Integer.parseInt(input.substring(0, 2));

                    while (true) {
                        int invalidTime = invalidTreat.getStartHour();
                        int treatMaxWindow = invalidTreat.getMedTask().getMaxWindow();
                        if ((startHourChosen == invalidTime
                                || startHourChosen == (invalidTime + 1) % 24)
                                || (startHourChosen < invalidTime + 1)
                                || (startHourChosen > invalidTime + treatMaxWindow)) {
                            String illegalStartHourMessage = "<html><div style=\"text-align:center;\">"
                                    + "<p>Invalid start time. Cannot choose a start time that extends to the next day</p>"
                                    + "<p>Please choose a start time between "
                                    + "<span>" + (invalidTime + 2) + ":00" + "</span>"
                                    + " and "
                                    + "<span>" + ((invalidTime + treatMaxWindow) % 24) + ":00" + "</span>"
                                    + ", inclusive."
                                    + "</p>"
                                    + "</div></html>";

                            JLabel illegalStartHour = new JLabel(illegalStartHourMessage);

                            JOptionPane.showMessageDialog(null, illegalStartHour);

                            break;
                        } else {
                            try {
                                schedule.moveTreatment(invalidTreat, startHourChosen);
                            } catch (SQLException e1) {
                                JOptionPane.showMessageDialog(null,
                                        "Error reading from database. Please call your system administrator.");
                                e1.printStackTrace();
                            }
                            break;
                        }
                    }
                }
            } catch (ImpossibleScheduleException e1) {
                JLabel impSchedException = new JLabel(e1.getMessage());
                JOptionPane.showMessageDialog(null, impSchedException,
                        "Error", JOptionPane.ERROR_MESSAGE);
                return "";
            }
        }
        schedule.calculateEfficient();
        String result = schedule.getScheduleResult();
        sb.append(result);

        // Check if backup volunteer is needed
        boolean backupVolunteerFound = result.contains("backup volunteer");
        if (backupVolunteerNeeded && backupVolunteerFound) {
            sb.append("\n\nBackup volunteer is needed. Please call them before continuing.");
            JOptionPane.showMessageDialog(null, "Backup volunteer is needed. Please call them before continuing.");
        }

        generateFile(sb.toString());
        return sb.toString();
    }

    /**
     * main
     * 
     * @param args
     */
    public static void main(String[] args) {
        final String formattedString = String.format("Welcome to the EWR program by Group 10 for ENSF 380.%n" +
                "This program displays different types of tasks, including:%n" +
                "- Medical tasks%n" +
                "- Normal tasks%n" +
                "Press Start to use the program!\n");

        final String message = String.format("\n DIRECTIONS: \n"
                + "- Enter a date and click 'Calculate' to check the schedule for that day.%n - Date format: yyyy-mm-dd");

        Object[] options = { "Start" };

        // Create a new dimension with custom width and height
        Dimension size = new Dimension(500, 400);

        // Create a new JOptionPane with custom size
        JOptionPane optionPane = new JOptionPane(formattedString + message,
                JOptionPane.INFORMATION_MESSAGE,
                JOptionPane.YES_OPTION,
                null,
                options);

        optionPane.setPreferredSize(size);

        // Show the dialog and close the program when the user clicks "OK"
        JDialog dialog = optionPane.createDialog(null, "EWR Schedule.");
        dialog.setVisible(true);

        if (optionPane.getValue() == options[0]) {
            // Create an instance of the ScheduleGUI class
            new ScheduleGUI();
        } else {
            System.exit(0);
        }
    }

}

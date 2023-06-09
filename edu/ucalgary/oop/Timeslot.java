/**
 * Group number: 10
 * Shayyan Asim (30149567),
 * Angelo Jerome T. Reynante (30160763),
 * Masroor Posh (30156171),
 * Mohamad Hussein (30145507)
 * */
package edu.ucalgary.oop;

import java.util.ArrayList;

public class Timeslot {
    private boolean backupNeeded;
    private ArrayList<Task> tasks;
    private int timeAvailable;

    /**
     * default constructor.
     */
    public Timeslot() {
        this.backupNeeded = false;
        this.tasks = new ArrayList<Task>();
        this.timeAvailable = 60;
    }

    /**
     * Constructor
     * 
     * @param timeAvailable
     */
    public Timeslot(int timeAvailable) {
        this.backupNeeded = false;
        this.tasks = new ArrayList<Task>();
        this.timeAvailable = timeAvailable;
    }

    // Getters and Setters
    /**
     *
     * @return backup needed
     */
    public boolean getBackupNeeded() {
        return this.backupNeeded;
    }

    /**
     *
     * @param backupNeeded
     */
    public void setBackupNeeded(boolean backupNeeded) {
        this.backupNeeded = backupNeeded;
    }

    /**
     *
     * @return time available
     */
    public int getTimeAvailable() {
        return this.timeAvailable;
    }

    /**
     *
     * @param timeAvailable
     */
    public void setTimeAvailable(int timeAvailable) {
        this.timeAvailable = timeAvailable;
    }

    /**
     * Add time
     * 
     * @param timeAvailable
     * @throws IllegalArgumentException
     */
    public void addTimeAvailable(int timeAvailable) throws IllegalArgumentException {
        this.timeAvailable += timeAvailable;
        if (this.timeAvailable < 0) {
            throw new IllegalArgumentException("The time available became negative!");
        }
    }

    /**
     * Getter.
     * 
     * @return tasks
     */
    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    /**
     *
     * @param tasks
     */
    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adding the Task.
     * 
     * @param task
     */
    public void addTask(Task task) {
        this.tasks.add(task);
    }
}

/**
 * Group number: 10
 * Shayyan Asim (30149567),
 * Angelo Jerome T. Reynante (30160763),
 * Masroor Posh (30156171),
 * Mohamad Hussein (30145507)
 * */
package edu.ucalgary.oop;

public class MedicalTask extends Task {
    private int taskID;

    /**
     * Constructor
     * 
     * @param description
     * @param duration
     * @param maxWindow
     * @param taskID
     * @param animalNickname
     * @throws IllegalArgumentException
     */
    public MedicalTask(String description, int duration, int maxWindow, int taskID, String animalNickname)
            throws IllegalArgumentException {
        super(description, duration, maxWindow, animalNickname);

        if (taskID < 0) {
            throw new IllegalArgumentException("Task ID cannot be negative");
        }
        this.taskID = taskID;
    }

    /**
     * Getter
     * 
     * @return taskID
     */
    public int getTaskID() {
        return taskID;
    }

    /**
     *
     * @param taskID
     */
    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }
}
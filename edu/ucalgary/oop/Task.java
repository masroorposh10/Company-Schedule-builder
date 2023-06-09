/**
 * Group number: 10
 * Shayyan Asim (30149567),
 * Angelo Jerome T. Reynante (30160763),
 * Masroor Posh (30156171),
 * Mohamad Hussein (30145507)
 * */
package edu.ucalgary.oop;

public abstract class Task {
    private String description;
    private int duration;
    private int maxWindow;
    private String animalNickname;

    /**
     * Constructor
     * 
     * @param description
     * @param duration
     * @param maxWindow
     * @param animalNickname
     * @throws IllegalArgumentException
     */
    public Task(String description, int duration, int maxWindow, String animalNickname)
            throws IllegalArgumentException {
        if (description == null) {
            throw new IllegalArgumentException("Description cannot be null");
        }
        if (duration < 0) {
            throw new IllegalArgumentException("Duration cannot be negative");
        }
        if (maxWindow < 0) {
            throw new IllegalArgumentException("Max Window cannot be negative");
        }
        this.description = description;
        this.duration = duration;
        this.maxWindow = maxWindow;
        this.animalNickname = animalNickname;
    }

    // Getters
    /**
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     *
     * @param duration
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     *
     * @return max window
     */
    public int getMaxWindow() {
        return maxWindow;
    }

    /**
     *
     * @param maxWindow
     */
    public void setMaxWindow(int maxWindow) {
        this.maxWindow = maxWindow;
    }

    /**
     *
     * @return animal nickname.
     */
    public String getAnimalNickname() {
        return animalNickname;
    }

    /**
     *
     * @param animalNickname
     */
    public void setAnimalNickname(String animalNickname) {
        this.animalNickname = animalNickname;
    }
}
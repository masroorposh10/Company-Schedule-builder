/**
 * Group number: 10
 * Shayyan Asim (30149567),
 * Angelo Jerome T. Reynante (30160763),
 * Masroor Posh (30156171),
 * Mohamad Hussein (30145507)
 * */
package edu.ucalgary.oop;

public class FeedingTask extends Task {
    private final String species;

    /**
     * Constructor
     * 
     * @param description
     * @param duration
     * @param maxWindow
     * @param animalNickname
     * @param species
     * @throws IllegalArgumentException
     */
    public FeedingTask(String description, int duration, int maxWindow, String animalNickname, String species)
            throws IllegalArgumentException {
        super(description, duration, maxWindow, animalNickname);
        this.species = species;
    }

    public String getSpecies() {
        return this.species;
    }
}

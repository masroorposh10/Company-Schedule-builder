/**
 * Group number: 10
 * Shayyan Asim (30149567),
 * Angelo Jerome T. Reynante (30160763),
 * Masroor Posh (30156171),
 * Mohamad Hussein (30145507)
 * */
package edu.ucalgary.oop;

public class Animal {
    private String animalSpecies;
    private String animalNickname;
    private int animalID;
    private String activityPatternType;

    /**
     * 
     * @param animalID
     * @param animalSpecies
     * @param animalNickname
     * @param activityPatternType
     * @throws IllegalArgumentException
     */
    public Animal(int animalID, String animalSpecies, String animalNickname,
            String activityPatternType) throws IllegalArgumentException {
        if (animalID < 0) {
            throw new IllegalArgumentException("Animal ID cannot be negative");
        }
        if (animalSpecies == null) {
            throw new IllegalArgumentException("Animal species cannot be null");
        }
        if (animalNickname == null) {
            throw new IllegalArgumentException("Animal nickname cannot be null");
        }
        if (activityPatternType == null) {
            throw new IllegalArgumentException("Activity pattern type cannot be null");
        }
        this.animalID = animalID;
        this.animalSpecies = animalSpecies;
        this.animalNickname = animalNickname;
        this.activityPatternType = activityPatternType;
    }

    // getters

    /**
     *
     * @return animal ID
     */
    public int getAnimalID() {
        return animalID;
    }

    /**
     *
     * @param animalID
     */
    public void setAnimalID(int animalID) {
        this.animalID = animalID;
    }

    /**
     *
     * @return animal species
     */
    public String getAnimalSpecies() {
        return animalSpecies;
    }

    /**
     *
     * @param animalSpecies
     */
    public void setAnimalSpecies(String animalSpecies) {
        this.animalSpecies = animalSpecies;
    }

    /**
     *
     * @return animal nickname
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

    /**
     *
     * @return activity patterntype
     */
    public String getActivityPatternType() {
        return activityPatternType;
    }

    /**
     *
     * @param activityPatternType
     */
    public void setActivityPatternType(String activityPatternType) {
        this.activityPatternType = activityPatternType;
    }
}
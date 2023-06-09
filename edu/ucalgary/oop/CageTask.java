/**
 * Group number: 10
 * Shayyan Asim (30149567),
 * Angelo Jerome T. Reynante (30160763),
 * Masroor Posh (30156171),
 * Mohamad Hussein (30145507)
 * */
package edu.ucalgary.oop;

public class CageTask extends Task {

    /**
     *
     * @param description
     * @param duration
     * @param maxWindow
     * @param animalNickname
     * @throws IllegalArgumentException
     */
    public CageTask(String description, int duration, int maxWindow, String animalNickname)
            throws IllegalArgumentException {
        super(description, duration, maxWindow, animalNickname);
    }

}

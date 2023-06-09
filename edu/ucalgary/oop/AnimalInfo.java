/**
 * Group number: 10
 * Shayyan Asim (30149567),
 * Angelo Jerome T. Reynante (30160763),
 * Masroor Posh (30156171),
 * Mohamad Hussein (30145507)
 * */
package edu.ucalgary.oop;

enum AnimalInfo {

    COYOTE(5, 10, 5, "crepuscular"),
    PORCUPINE(5, 0, 10, "crepuscular"),
    FOX(5, 5, 5, "nocturnal"),
    RACCOON(5, 0, 5, "nocturnal"),
    BEAVER(5, 0, 5, "diurnal");

    public final int feedingPrepDuration;
    public final int feedingDuration;
    public final int cleanCageDuration;
    public final String activityPattern;

    /**
     * Constructor
     * 
     * @param feedingDuration
     * @param feedingPrepDuration
     * @param cleanCageDuration
     * @param activityPattern
     */
    AnimalInfo(int feedingDuration, int feedingPrepDuration, int cleanCageDuration, String activityPattern) {
        this.feedingPrepDuration = feedingPrepDuration;
        this.feedingDuration = feedingDuration;
        this.cleanCageDuration = cleanCageDuration;
        this.activityPattern = activityPattern;
    }

    /**
     * Getting the activity pattern
     * 
     * @param animal
     * @return
     */
    public static String getActivityPattern(String animal) {
        for (AnimalInfo a : AnimalInfo.values()) {
            if (a.name().equalsIgnoreCase(animal)) {
                return a.activityPattern;
            }
        }
        return null;
    }

    /**
     * Getting Feeding Prep duration.
     * 
     * @param animal
     * @return
     */
    public static int getFeedingPrepDuration(String animal) {
        for (AnimalInfo a : AnimalInfo.values()) {
            if (a.name().equalsIgnoreCase(animal)) {
                return a.feedingPrepDuration;
            }
        }
        return 0;
    }

    /**
     * Getting clean cage duration
     * 
     * @param animal
     * @return
     */
    public static int getCleanCageDuration(String animal) {
        for (AnimalInfo a : AnimalInfo.values()) {
            if (a.name().equalsIgnoreCase(animal)) {
                return a.cleanCageDuration;
            }
        }
        return 0;
    }

    /**
     * Getting feeding duration
     * 
     * @param animal
     * @return
     */
    public static int getFeedingDuration(String animal) {
        for (AnimalInfo a : AnimalInfo.values()) {
            if (a.name().equalsIgnoreCase(animal)) {
                return a.feedingDuration;
            }
        }
        return 0;
    }
}
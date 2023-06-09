/**
 * Group number: 10
 * Shayyan Asim (30149567),
 * Angelo Jerome T. Reynante (30160763),
 * Masroor Posh (30156171),
 * Mohamad Hussein (30145507)
 * */
package edu.ucalgary.oop;

public class Treatment {
    private Animal treatedAnimal;
    private MedicalTask medTask;
    private int startHour;

    /**
     * Constructor.
     * 
     * @param medicalTask
     * @param treatedAnimal
     * @param startHour
     */
    public Treatment(MedicalTask medicalTask, Animal treatedAnimal, int startHour) {
        this.medTask = medicalTask;
        this.treatedAnimal = treatedAnimal;
        this.startHour = startHour;
    }

    /**
     *
     * @return startHour
     */
    public int getStartHour() {
        return this.startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    /**
     *
     * @return treated Animal
     */
    public Animal getTreatedAnimal() {
        return this.treatedAnimal;
    }

    /**
     *
     * @param treatedAnimal
     */
    public void setTreatedAnimal(Animal treatedAnimal) {
        this.treatedAnimal = treatedAnimal;
    }

    /**
     *
     * @return medTask
     */
    public MedicalTask getMedTask() {
        return this.medTask;
    }

    /**
     *
     * @param medTask
     */
    public void setMedTask(MedicalTask medTask) {
        this.medTask = medTask;
    }
}

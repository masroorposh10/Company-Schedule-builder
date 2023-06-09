package edu.ucalgary.oop;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.regex.*;

public class TreatmentTest {
    Animal expectedTreatedAnimal = new Animal(0, "Reptiles", "Nichole", "Yes");
    MedicalTask expectedMedicalTask = new MedicalTask("description", 15, 30, 13212318, "John");
    int expectedStartHour = 0;
    Treatment testObject1 = new Treatment(expectedMedicalTask, expectedTreatedAnimal, expectedStartHour);

    @Test
    /**
     * Tests the Getter
     */
    public void testGetTreatedAnimal() {
        Animal actualTreatedAnimal = testObject1.getTreatedAnimal();
        assertEquals("Incorrect information stored/returned for description", expectedTreatedAnimal,
                actualTreatedAnimal);
    }

    @Test
    /**
     * tests the setter
     */
    public void testSetTreatedAnimal() {
        testObject1.setTreatedAnimal(expectedTreatedAnimal);
        assertEquals("Incorrect information stored/returned for description", expectedTreatedAnimal,
                testObject1.getTreatedAnimal());
    }

    @Test
    /**
     * tests the TreatmentConstructor
     */
    public void testTreatmentConstructor() {
        Animal actualTreatedAnimal = testObject1.getTreatedAnimal();
        assertEquals("Incorrect information stored/returned for description", expectedTreatedAnimal,
                actualTreatedAnimal);

    }

    @Test
    /**
     * tests the GetMedTask
     */
    public void testGetMedTask() {
        MedicalTask actualMedicalTask = testObject1.getMedTask();
        assertEquals("Incorrect information stored/returned for description", expectedMedicalTask, actualMedicalTask);
    }

    @Test
    /**
     * tests the SetMedTask
     */
    public void testSetMedTask() {
        testObject1.setMedTask(expectedMedicalTask);
        assertEquals("Incorrect information stored/returned for description", expectedMedicalTask,
                testObject1.getMedTask());
    }

    @Test
    /**
     * tests the GetStartHour
     */
    public void testGetStartHour() {
        int actualStartHour = testObject1.getStartHour();

        assertEquals("Incorrect information stored/returned for description", testObject1.getStartHour(),
                actualStartHour);

    }

    @Test
    /**
     * test the SetStartHour
     */
    public void testSetStartHour() {
        int expectedStartHour = 10;
        testObject1.setStartHour(expectedStartHour);
        assertEquals("Incorrect information stored/returned for description", expectedStartHour,
                testObject1.getStartHour());
    }
}

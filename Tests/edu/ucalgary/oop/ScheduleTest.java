/**
 * Group number: 10
 * Shayyan Asim (30149567),
 * Angelo Jerome T. Reynante (30160763),
 * Masroor Posh (30156171),
 * Mohamad Hussein (30145507)
 * */
package edu.ucalgary.oop;

import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class ScheduleTest{
    LocalDate expecteDate = LocalDate.of(2020, 12, 12);
    HashMap<Integer, Timeslot> expectedSchedule = new HashMap<>();
    String expectedScheduleResult = "Schedule for " + expecteDate + "\n\n";
    int expectedDay = 12;
    int expectedMonth = 12;
    int expectedYear = 2020;
    Schedule testObject1 = new Schedule(expectedDay, expectedMonth, expectedYear);
    Animal testAnimal = new Animal(10, "test species", 
        "test nickname", "test activity pattern");
    MedicalTask testMedical = new MedicalTask("test", 
        50, 5, 10, "test nickname");
    TreeMap <Integer, ArrayList<Treatment>> expectedTreatmentTasks = new TreeMap<>();
    private final CageTask expectedCageTask = new CageTask("cage clean",1,2,"nickname");
    private int expectedStartHour = 10;





    /* This is assuming the database is already initialized */
    @Test
    /**
     * testReadSql
     */
    public void testReadSql(){
        boolean worked = true;
        try{
            testObject1.readSql();
        }catch(Exception e){
            e.printStackTrace();
            worked = false;
        }
        assertTrue("The database did not connect", worked);
    }
    


    /*
     * This is to test out if there are too many treatments
     * that overlap more than 2 hours
     */
    @Test
    /**
     * testInvalidTreatments
     */
    public void testInvalidTreatments(){
        /* Duration = 50 + 50 + 50 which spans 2h (2h 30m) */
        boolean thrown = false;
        int startHour = 1;
        Treatment testTreat1 = new Treatment(testMedical,testAnimal,startHour);
        Treatment testTreat2 = new Treatment(testMedical,testAnimal,startHour);
        Treatment testTreat3 = new Treatment(testMedical,testAnimal,startHour);
        
        Schedule schedule = new Schedule(expectedDay, expectedMonth, expectedYear);
        schedule.addTreatment(testTreat3, startHour, false);
        schedule.addTreatment(testTreat2, startHour, false);
        schedule.addTreatment(testTreat1, startHour, false);

        
        try {
            schedule.makeEfficientSchedule();
        } catch (IllegalTaskException e) {
            thrown = true;
        } catch(Exception e){
            e.printStackTrace();
        }
        assertTrue("The treatments were not invalid and an exception of " +
            "IllegalTaskException was not thrown", thrown);
        
    }


    /**
     * testInvalidTreatmentsGUI
     */
    public void testInvalidTreatmentsGUI(){
        boolean thrown = false;
        int startHour = 1;
        Treatment testTreat1 = new Treatment(testMedical,testAnimal,startHour);
        Treatment testTreat2 = new Treatment(testMedical,testAnimal,startHour);
        Treatment testTreat3 = new Treatment(testMedical,testAnimal,startHour);
        
        Schedule schedule = new Schedule(expectedDay, expectedMonth, expectedYear);
        schedule.addTreatment(testTreat3, startHour, false);
        schedule.addTreatment(testTreat2, startHour, false);
        schedule.addTreatment(testTreat1, startHour, false);

        new ScheduleGUI(schedule);
    }


    /**
     * testImpossibleScheduleGUI
     */
    public void testImpossibleScheduleGUI(){
        MedicalTask testMedical = new MedicalTask("test", 
            50, 1, 10, "test nickname");
        boolean thrown = false;
        int startHour = 1;
        Treatment testTreat1 = new Treatment(testMedical,testAnimal,startHour);
        Treatment testTreat2 = new Treatment(testMedical,testAnimal,startHour);
        Treatment testTreat3 = new Treatment(testMedical,testAnimal,startHour);
        
        Schedule schedule = new Schedule(expectedDay, expectedMonth, expectedYear);
        schedule.addTreatment(testTreat3, startHour, false);
        schedule.addTreatment(testTreat2, startHour, false);
        schedule.addTreatment(testTreat1, startHour, false);

        new ScheduleGUI(schedule);
    }

    /**
     * main method.
     * @param args
     */
    public static void main(String[] args){
        // ScheduleGUI.main(args);
        /* Testing moving treatments */
        ScheduleTest obj = new ScheduleTest();
        obj.testInvalidTreatmentsGUI();

        /* Testing too impossible schedule */
        // obj.testImpossibleScheduleGUI();
    }

    @Test
    /**
     * testGetTreatmentList
     */
    public void testGetTreatmentList() {
        ArrayList<Treatment> expTreatmentTasks = new ArrayList<Treatment>();

        ArrayList<Treatment> actualTreatmentTasks = testObject1.getTreatmentList();
        assertEquals("Incorrect information stored/returned for description", expTreatmentTasks, actualTreatmentTasks); 
    }

    
    @Test
    /**
     * testCalculateEfficient
     */
    public void testCalculateEfficient() {
        String actualScheduleResult = testObject1.calculateEfficient();
        assertEquals("Incorrect information stored/returned for description", 
            expectedScheduleResult, actualScheduleResult); 
    }//not final

    @Test
    /** tests for an empty schedule result
     * testGetScheduleResult
     */
    public void testGetScheduleResult() {
        String actualScheduleResult = testObject1.getScheduleResult();
        assertEquals("Incorrect information stored/returned for description", 
            "", actualScheduleResult); 
    }

    @Test
    /**
     * testGetDay
     */
    public void testGetDay() {
        int actualDay = testObject1.getDay();
        assertEquals("Incorrect information stored/returned for description", 
        expectedDay, actualDay); 
    }
    
    @Test
    /**
     * testGetMonth
     */
    public void testGetMonth() {
        int actualMonth = testObject1.getMonth();
        assertEquals("Incorrect information stored/returned for description", 
            expectedMonth, actualMonth); 
    }
    
    @Test
    /**
     * testGetYear
     */
    public void testGetYear() {
        int actualYear = testObject1.getYear();
        assertEquals("Incorrect information stored/returned for description", 
            expectedYear, actualYear); 
        }
    
    @Test
    /**
     * testGetDate
     */
    public void testGetDate() {
        LocalDate actualDate = testObject1.getDate();
        assertEquals("Incorrect information stored/returned for description", expecteDate, actualDate); 
    }

    @Test
    /**
     * testScheduleConstructor
     */
    public void testScheduleConstructor() {
        boolean noExceptionThrown = true;
        try{
            Schedule testObject2 = new Schedule(1, 1, 2023);
        } catch (Exception e) {
            noExceptionThrown = false;
        }
        assertTrue("Schedule constructor is cannot be instantiated with valid values", 
            noExceptionThrown);
    }

    @Test

    /**
     * test Set Date.
     */
    public void testSetDate(){
        testObject1.setDate(expecteDate);
        assertEquals(testObject1.getDate(),expecteDate);
    }
    @Test
    /**
     * Set Year
     */
    public void testSetYear(){
        testObject1.setYear(expectedYear);
        assertEquals(testObject1.getYear(),expectedYear);
    }
    @Test
    /**
     * Set Month
     */
    public void testSetMonth(){
        testObject1.setMonth(expectedMonth);
        assertEquals(testObject1.getMonth(),expectedMonth);
    }
    @Test
    /**
     * test Set Day
     */
    public void testSetDay(){
        testObject1.setDay(expectedDay);
        assertEquals(testObject1.getDay(),expectedDay);
    }
    @Test
    /**
     * testSetTreatmentTasks
     */
    public void testSetTreatmentTasks(){
        testObject1.setTreatmentTasks(expectedTreatmentTasks);
        assertEquals(testObject1.getTreatmentTasks(),expectedTreatmentTasks);
    }
    @Test
    /**
     * testSetSchedule
     */
    public void testSetSchedule(){
        testObject1.calculateEfficient();
        testObject1.setSchedule(expectedSchedule);
        assertEquals(testObject1.getScheduleResult(),expectedScheduleResult);
    }
    @Test
    /**
     * testClearSchedule
     */
    public void testClearSchedule() {
        /* Populate the schedule first */
        try {
            testObject1.readSql();
            testObject1.makeEfficientSchedule();

        } catch (SQLException e) {
            assertTrue("There was an error reading from the database", false);
        } catch (IllegalTaskException | ImpossibleScheduleException e){
            assertTrue("There are illegal tasks", false);
        }
        // Verify that all timeslots are empty
        testObject1.clearSchedule();
        HashMap<Integer, Timeslot> schedule = testObject1.getSchedule();
        for (int i = 0; i < 24; i++) {
            assertTrue(schedule.get(i).getTasks().isEmpty());
        }
    }
    

    @Test
    /**
     * testAddTasks
     */
    public void testAddTasks(){
        Schedule testObject2 = new Schedule(1, 1, 2023);
        try {
            testObject1.readSql();
            testObject2.readSql();
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        testObject1.addTask(expectedCageTask,expectedStartHour);

        int testObject1Count = 0;
        int testObject2Count = 0;
        for (int i = 0; i < 24; i++) {
            testObject1Count += testObject1.getSchedule().get(i).getTasks().size();
            testObject2Count += testObject2.getSchedule().get(i).getTasks().size();
        }
        assertNotEquals("The schedules have the same number of tasks, obj1: " + 
            testObject1Count + " and obj2: " + testObject2Count, testObject1Count, testObject2Count);

       

    }

}
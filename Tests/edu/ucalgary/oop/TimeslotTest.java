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

import java.util.ArrayList;

public class TimeslotTest {

    Timeslot testObject1 = new Timeslot();
    Timeslot testObject2 = new Timeslot(60);

    private class TimeTask extends Task {
        public TimeTask(String description, int duration, int maxWindow, int taskID, String animalNickname) {
            super(description, duration, maxWindow, animalNickname);
        }
    }

    @Test
    /**
     * testDefaultConstructor
     */
    public void testDefaultConstructor() {
        assertFalse(testObject1.getBackupNeeded());
        assertEquals("Default Constructor didn't get the expected results", 60, testObject1.getTimeAvailable());
        assertTrue(testObject1.getTasks().isEmpty());
    }

    @Test
    /**
     * testConstructor
     */
    public void testConstructor() {
        assertFalse(testObject2.getBackupNeeded());
        assertEquals("Constructor didn't get expected results", 60, testObject2.getTimeAvailable());
        assertTrue(testObject2.getTasks().isEmpty());
    }

    @Test
    /**
     * testSetBackupNeeded
     */
    public void testSetBackupNeeded() {
        testObject1.setBackupNeeded(true);
        assertTrue("Didn't set the Backup Needed", testObject1.getBackupNeeded());
    }

    @Test
    /**
     * testGetBackupNeeded
     */
    public void testGetBackupNeeded() {
        assertFalse("Didn't get the Backup Needed", testObject1.getBackupNeeded());
    }

    @Test
    /**
     * testSetTimeAvailable
     */
    public void testSetTimeAvailable() {
        testObject1.setTimeAvailable(90);
        assertEquals("Setter didn't give expected results", 90, testObject1.getTimeAvailable());
    }

    @Test
    /**
     * testGetTimeAvailable
     */
    public void testGetTimeAvailable() {
        assertEquals("Get time available didn't give expected results: ", 60, testObject1.getTimeAvailable());
    }

    @Test
    /**
     * testGetTasks
     */
    public void testGetTasks() {
        ArrayList<Task> tasks = new ArrayList<Task>();
        testObject1.setTasks(tasks);
        assertEquals("Getter for Tasks didn't work", tasks, testObject1.getTasks());
    }

    @Test
    /**
     * testAddTimeAvailable
     */
    public void testAddTimeAvailable() {
        testObject1.addTimeAvailable(30);
        assertEquals("Add time didn't give expected Results:", 90, testObject1.getTimeAvailable());
        assertThrows(IllegalArgumentException.class, () -> testObject1.addTimeAvailable(-100));
    }

    @Test
    /**
     * testAddTimeAvailable
     */
    public void testSetTasks() {
        ArrayList<Task> tasks = new ArrayList<Task>();
        testObject1.setTasks(tasks);
        assertEquals("Didn't Set Tasks", tasks, testObject1.getTasks());
    }

    @Test
    /**
     * testAddTask
     */
    public void testAddTask() {
        ArrayList<Task> tasks = new ArrayList<Task>();
        Task task = new TimeTask("test", 30, 60, 1, "test");
        tasks.add(task);
        testObject1.addTask(task);
        assertEquals("Didn't add the tasks:", tasks, testObject1.getTasks());
    }

}

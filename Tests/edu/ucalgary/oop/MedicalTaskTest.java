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

public class MedicalTaskTest {

    @Test
    /**
     * testConstructor
     */
    public void testConstructor() {
        MedicalTask task = new MedicalTask("Task Description", 60, 120, 1, "Animal Nickname");
        assertEquals("Task Description", task.getDescription());
        assertEquals(60, task.getDuration());
        assertEquals(120, task.getMaxWindow());
        assertEquals(1, task.getTaskID());
        assertEquals("Animal Nickname", task.getAnimalNickname());
    }

    @Test
    /**
     * testSetTaskID
     */
    public void testSetTaskID() {
        MedicalTask task = new MedicalTask("Task Description", 60, 120, 1, "Animal Nickname");
        task.setTaskID(2);
        assertEquals(2, task.getTaskID());
    }

    @Test
    /**
     * testNegativeTaskID
     */
    public void testNegativeTaskID() {
        assertThrows(IllegalArgumentException.class, () -> {
            MedicalTask task = new MedicalTask("Task Description", 60, 120, -1, "Animal Nickname");
        });
    }
}

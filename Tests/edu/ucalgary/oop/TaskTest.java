/**
 * Group number: 10
 * Shayyan Asim (30149567),
 * Angelo Jerome T. Reynante (30160763),
 * Masroor Posh (30156171),
 * Mohamad Hussein (30145507)
 * */

package edu.ucalgary.oop;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import java.util.regex.*;

public class TaskTest {

    private class ConcreteTask extends Task {
        /**
         *
         * @param description
         * @param duration
         * @param maxWindow
         * @param animalNickname
         */
        public ConcreteTask(String description, int duration, int maxWindow, String animalNickname) {
            super(description, duration, maxWindow, animalNickname);
        }

        @Test
        /**
         * testTaskConstructor
         */
        public void testTaskConstructor() {
            Task task = new ConcreteTask("Feeding the animals", 30, 9, "Fluffy");
            assertEquals("Feeding the animals", task.getDescription());
            assertEquals(30, task.getDuration());
            assertEquals(9, task.getMaxWindow());
            assertEquals("Fluffy", task.getAnimalNickname());
        }

        @Test
        /**
         * testTaskConstructor
         */
        public void testTaskSetters() {
            Task task = new ConcreteTask("Feeding the animals", 30, 9, "Fluffy");
            task.setDescription("Cleaning the cages");
            task.setDuration(45);
            task.setMaxWindow(18);
            task.setAnimalNickname("Fido");
            assertEquals("Cleaning the cages", task.getDescription());
            assertEquals(45, task.getDuration());
            assertEquals(18, task.getMaxWindow());
            assertEquals("Fido", task.getAnimalNickname());
        }

        @Test
        /**
         * testTaskConstructor
         */
        public void testTaskToString() {
            Task task = new ConcreteTask("Feeding the animals", 30, 9, "Fluffy");
            String patternString = "^Task\\{description='Feeding the animals', duration=30, maxWindow=9, animalNickname='Fluffy'\\}$";
            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher = pattern.matcher(task.toString());
            assertTrue(matcher.matches());
        }
    }
}

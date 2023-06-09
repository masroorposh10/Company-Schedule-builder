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

public class AnimalTest {
    @Test(expected = IllegalArgumentException.class)
    /**
     * test the ConstructorWithNegativeAnimalID
     */
    public void testConstructorWithNegativeAnimalID() {
        new Animal(-1, "species", "nickname", "activityPattern");
    }

    @Test(expected = IllegalArgumentException.class)
    /**
     * test the ConstructorWithNullAnimalSpecies
     */
    public void testConstructorWithNullAnimalSpecies() {
        new Animal(1, null, "nickname", "activityPattern");
    }

    @Test(expected = IllegalArgumentException.class)
    /**
     * test the ConstructorWithNullAnimalNickname
     */
    public void testConstructorWithNullAnimalNickname() {
        new Animal(1, "species", null, "activityPattern");
    }

    @Test(expected = IllegalArgumentException.class)
    /**
     * test the ConstructorWithNullActivityPatternType
     */
    public void testConstructorWithNullActivityPatternType() {
        new Animal(1, "species", "nickname", null);
    }

    @Test
    /**
     * test the GettersAndSetters
     */
    public void testGettersAndSetters() {
        Animal animal = new Animal(1, "species", "nickname", "activityPattern");

        assertEquals(1, animal.getAnimalID());
        assertEquals("species", animal.getAnimalSpecies());
        assertEquals("nickname", animal.getAnimalNickname());
        assertEquals("activityPattern", animal.getActivityPatternType());

        animal.setAnimalID(2);
        animal.setAnimalSpecies("new species");
        animal.setAnimalNickname("new nickname");
        animal.setActivityPatternType("new activity pattern");

        assertEquals(2, animal.getAnimalID());
        assertEquals("new species", animal.getAnimalSpecies());
        assertEquals("new nickname", animal.getAnimalNickname());
        assertEquals("new activity pattern", animal.getActivityPatternType());
    }
}

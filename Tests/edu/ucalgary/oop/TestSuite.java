/**
 * Group number: 10
 * Shayyan Asim (30149567),
 * Angelo Jerome T. Reynante (30160763),
 * Masroor Posh (30156171),
 * Mohamad Hussein (30145507)
 * */
package edu.ucalgary.oop;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        MedicalTaskTest.class,
        AnimalTest.class,
        TreatmentTest.class,
        ScheduleTest.class,
        TimeslotTest.class
})

public class TestSuite {
    // the class remains empty,
    // used only as a holder for the above annotations
}
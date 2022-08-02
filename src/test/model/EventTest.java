package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

// This class has been taken from the Alarm System project:
// https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git
// It has been edited according to the needs of this project
public class EventTest {
    private static final int HASH_CONSTANT = 13;
    private String description;
    private Event e;
    private Date d;

    //NOTE: these tests might fail if time at which line (2) below is executed
    //is different from time that line (1) is executed.  Lines (1) and (2) must
    //run in same millisecond for this test to make sense and pass.

    @BeforeEach
    public void runBefore() {
        e = new Event("New Booking added");   // (1)
        d = Calendar.getInstance().getTime();   // (2)
        description = e.getDescription();
    }

    @Test
    public void testEvent() {
        assertEquals("New Booking added", e.getDescription());
        assertEquals(d, e.getDate());
    }

    @Test
    public void testToString() {
        assertEquals(d.toString() + "\n" + "New Booking added", e.toString());
    }

    @Test
    public void testEquals() {
        assertFalse(e.equals(null));
        assertFalse(e.equals("ABCD"));
    }

    @Test
    public void testHash() {
        assertEquals(e.hashCode(), HASH_CONSTANT * d.hashCode() + description.hashCode());
    }
}

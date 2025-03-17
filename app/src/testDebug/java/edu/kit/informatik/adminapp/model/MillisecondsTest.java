package edu.kit.informatik.adminapp.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.kit.informatik.adminapp.model.resources.Errors;

/**
 * This class tests the {@link Milliseconds} class.
 */
public class MillisecondsTest {
    private int[] validMilliseconds = {0, 10891723, Integer.MAX_VALUE};
    private int[] invalidMilliseconds = {-1, -76123, Integer.MIN_VALUE};

    /**
     * Tests whether valid Milliseconds objects can be created.
     */
    @Test
    public void testValidMilliseconds() {
        for (int validMilliseconds: validMilliseconds) {
            new Milliseconds(validMilliseconds);
        }
    }

    /**
     * Tests whether invalid Milliseconds objects throw an exception.
     */
    @Test
    public void testInvalidMilliseconds() {
        for (int invalidMilliseconds : invalidMilliseconds) {
            Exception e = assertThrows(IllegalArgumentException.class,
                    () -> new Milliseconds(invalidMilliseconds));
            assertEquals(String.format(Errors.NEGATIVE_NUMBER, invalidMilliseconds), e.getMessage());
        }
    }

    /**
     * Tests the asInt() method.
     */
    @Test
    public void testAsInt() {
        for (int validMilliseconds : validMilliseconds) {
            Milliseconds milliseconds = new Milliseconds(validMilliseconds);
            assertEquals(validMilliseconds, milliseconds.asInt());
        }
    }

    /**
     * Tests the equals method for reflexivity.
     */
    @Test
    public void testEquals_self() {
        Milliseconds milliseconds1 = new Milliseconds(validMilliseconds[0]);
        assertTrue(milliseconds1.equals(milliseconds1));
    }

    /**
     * Tests the equals method for two identical objects.
     */
    @Test
    public void testEquals_same() {
        Milliseconds milliseconds1 = new Milliseconds(validMilliseconds[0]);
        Milliseconds milliseconds2 = new Milliseconds(validMilliseconds[0]);
        assertTrue(milliseconds1.equals(milliseconds2));
        assertTrue(milliseconds2.equals(milliseconds1));
    }

    /**
     * Tests the equals method for two different objects.
     */
    @Test
    public void testEquals_different() {
        Milliseconds milliseconds1 = new Milliseconds(validMilliseconds[0]);
        Milliseconds milliseconds2 = new Milliseconds(validMilliseconds[1]);
        assertFalse(milliseconds1.equals(milliseconds2));
        assertFalse(milliseconds2.equals(milliseconds1));
    }

    /**
     * Tests the equals method with a null object.
     */
    @Test
    public void testEquals_null() {
        Milliseconds milliseconds1 = new Milliseconds(validMilliseconds[0]);
        assertFalse(milliseconds1.equals(null));
    }

    /**
     * Tests whether two identical objects have the same hash code.
     */
    @Test
    public void testHashCode_same() {
        Milliseconds milliseconds1 = new Milliseconds(validMilliseconds[0]);
        Milliseconds milliseconds2 = new Milliseconds(validMilliseconds[0]);
        assertEquals(milliseconds1.hashCode(), milliseconds2.hashCode());
    }

    /**
     * Tests whether two different objects have different hash codes.
     */
    @Test
    public void testHashCode_different() {
        Milliseconds milliseconds1 = new Milliseconds(validMilliseconds[0]);
        Milliseconds milliseconds2 = new Milliseconds(validMilliseconds[1]);
        assertNotEquals(milliseconds1.hashCode(), milliseconds2.hashCode());
    }

    /**
     * Tests the toString method.
     */
    @Test
    public void testToString() {
        for (int validMilliseconds : validMilliseconds) {
            Milliseconds milliseconds = new Milliseconds(validMilliseconds);
            assertEquals(String.valueOf(validMilliseconds), milliseconds.toString());
        }
    }
}

package edu.kit.informatik.adminapp.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.kit.informatik.adminapp.model.resources.Errors;

/**
 * This class tests the {@link Port} class.
 */
public class PortTest {
    private int[] validPorts = {0, 10891723, Integer.MAX_VALUE};
    private int[] invalidPorts = {-1, -76123, Integer.MIN_VALUE};

    /**
     * Tests whether valid ports can be created.
     */
    @Test
    public void testValidPort() {
        for (int validPort : validPorts) {
            new Port(validPort);
        }
    }

    /**
     * Tests whether invalid ports throw an exception.
     */
    @Test
    public void testInvalidPort() {
        for (int invalidPort : invalidPorts) {
            Exception e = assertThrows(IllegalArgumentException.class,
                    () -> new Port(invalidPort));
            assertEquals(String.format(Errors.NEGATIVE_NUMBER, invalidPort), e.getMessage());
        }
    }

    /**
     * Tests the toInt() method.
     */
    @Test
    public void testToInt() {
        for (int validPort : validPorts) {
            Port port = new Port(validPort);
            assertEquals(validPort, port.toInt());
        }
    }

    /**
     * Tests the equals method for reflexivity.
     */
    @Test
    public void testEquals_self() {
        Port port1 = new Port(validPorts[0]);
        assertTrue(port1.equals(port1));
    }

    /**
     * Tests the equals method for two identical objects.
     */
    @Test
    public void testEquals_same() {
        Port port1 = new Port(validPorts[0]);
        Port port2 = new Port(validPorts[0]);
        assertTrue(port1.equals(port2));
        assertTrue(port2.equals(port1));
    }

    /**
     * Tests the equals method for two different objects.
     */
    @Test
    public void testEquals_different() {
        Port port1 = new Port(validPorts[0]);
        Port port2 = new Port(validPorts[1]);
        assertFalse(port1.equals(port2));
        assertFalse(port2.equals(port1));
    }

    /**
     * Tests the equals method with a null object.
     */
    @Test
    public void testEquals_null() {
        Port port1 = new Port(validPorts[0]);
        assertFalse(port1.equals(null));
    }

    /**
     * Tests whether two identical objects have the same hash code.
     */
    @Test
    public void testHashCode_same() {
        Port port1 = new Port(validPorts[0]);
        Port port2 = new Port(validPorts[0]);
        assertEquals(port1.hashCode(), port2.hashCode());
    }

    /**
     * Tests whether two different objects have different hash codes.
     */
    @Test
    public void testHashCode_different() {
        Port port1 = new Port(validPorts[0]);
        Port port2 = new Port(validPorts[1]);
        assertNotEquals(port1.hashCode(), port2.hashCode());
    }
}

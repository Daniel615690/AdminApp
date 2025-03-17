package edu.kit.informatik.adminapp.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.kit.informatik.adminapp.model.resources.Errors;

/**
 * This class tests the {@link Name} class.
 */
public class NameTest {
    private String[] validNames = {
            "Max Mustermann",
            "",
            "Barack Obama"
    };
    private String[] invalidNames = {
            null,
            "$",
            "Mustermann$Max$1$passwort$token1$0c511be079e1da00d974571e4abe2899f4e0a19bbeb46ca72a7" +
                    "3058e7d1e981e",
            "Mustermann$",
            "$Mustermann"
    };

    /**
     * Tests whether the Name constructor accepts valid parameters.
     */
    @Test
    public void testValidName() {
        for (String validName : validNames) {
            new Name(validName);
        }
    }

    /**
     * Tests whether the Name constructor throws an exception for invalid parameters.
     */
    @Test
    public void testInvalidName() {
        for (String invalidName : invalidNames) {
            Exception e = assertThrows(IllegalArgumentException.class,
                    () -> new Name(invalidName));
            assertEquals(String.format(Errors.REGEX_NOT_MATCHED, invalidName, Name.REGEX_NAME),
                    e.getMessage());
        }
    }

    /**
     * Tests the toString method.
     */
    @Test
    public void testToString() {
        for (String validName : validNames) {
            Name name = new Name(validName);
            assertEquals(validName, name.toString());
        }
    }

    /**
     * Tests the equals method for reflexivity.
     */
    @Test
    public void testEquals_self() {
        Name name = new Name(validNames[0]);
        assertTrue(name.equals(name));
    }

    /**
     * Tests the equals method for two identical objects.
     */
    @Test
    public void testEquals_same() {
        Name name1 = new Name(validNames[0]);
        Name name2 = new Name(validNames[0]);
        assertTrue(name1.equals(name2));
        assertTrue(name2.equals(name1));
    }

    /**
     * Tests the equals method with a null object.
     */
    @Test
    public void testEquals_null() {
        Name name1 = new Name(validNames[0]);
        assertFalse(name1.equals(null));
    }

    /**
     * Tests the equals method for two different objects.
     */
    @Test
    public void testEquals_different() {
        Name name1 = new Name(validNames[0]);
        Name name2 = new Name(validNames[1]);
        assertFalse(name1.equals(name2));
        assertFalse(name2.equals(name1));
    }

    /**
     * Tests whether two identical objects return the same hash code.
     */
    @Test
    public void testHashCode_true() {
        Name name1 = new Name(validNames[0]);
        Name name2 = new Name(validNames[0]);
        assertEquals(name1.hashCode(), name2.hashCode());
    }

    /**
     * Tests whether two different objects return different hash codes.
     */
    @Test
    public void testHashCode_false() {
        Name name1 = new Name(validNames[0]);
        Name name2 = new Name(validNames[1]);
        assertNotEquals(name1.hashCode(), name2.hashCode());
    }
}

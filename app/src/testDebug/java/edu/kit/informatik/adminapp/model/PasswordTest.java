package edu.kit.informatik.adminapp.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.kit.informatik.adminapp.model.resources.Errors;

/**
 * This class tests the {@link Password} class.
 */
public class PasswordTest {
    private String[] validPasswords = {
            "aksbasd",
            "&&!&/&/!)/&((=``",
            "21638712",
            "ASsahbda71263uas",
            "asiub 12n /(&"
    };
    private String[] invalidPasswords = {
            null,
            "kashbc$asu",
            "$ashb",
            "(!/$",
            "$"
    };

    /**
     * Tests whether creating valid passwords is possible.
     */
    @Test
    public void testValidPassword() {
        for (String validPassword : validPasswords) {
            new Password(validPassword);
        }
    }

    /**
     * Tests whether creating invalid passwords throws an exception.
     */
    @Test
    public void testInvalidPassword() {
        for (String invalidPassword : invalidPasswords) {
            Exception e = assertThrows(IllegalArgumentException.class,
                    () -> new Password(invalidPassword));
            assertEquals(String.format(Errors.REGEX_NOT_MATCHED, invalidPassword,
                    Password.REGEX_PASSWORD), e.getMessage());
        }
    }

    /**
     * Tests the toString method.
     */
    @Test
    public void testToString() {
        for (String validPassword : validPasswords) {
            Password password = new Password(validPassword);
            assertEquals(validPassword, password.toString());
        }
    }

    /**
     * Tests the equals method for reflexivity.
     */
    @Test
    public void testEquals_self() {
        Password password1 = new Password(validPasswords[0]);
        assertTrue(password1.equals(password1));
    }

    /**
     * Tests the equals method for identical passwords.
     */
    @Test
    public void testEquals_same() {
        Password password1 = new Password(validPasswords[0]);
        Password password2 = new Password(validPasswords[0]);
        assertTrue(password1.equals(password2));
        assertTrue(password2.equals(password1));
    }

    /**
     * Tests the equals method for different passwords.
     */
    @Test
    public void testEquals_different() {
        Password password1 = new Password(validPasswords[0]);
        Password password2 = new Password(validPasswords[1]);
        assertFalse(password1.equals(password2));
        assertFalse(password2.equals(password1));
    }

    /**
     * Tests the equals method with a null object.
     */
    @Test
    public void testEquals_null() {
        Password password1 = new Password(validPasswords[0]);
        assertFalse(password1.equals(null));
    }

    /**
     * Tests whether two identical objects return the same hash code.
     */
    @Test
    public void testHashCode_same() {
        Password password1 = new Password(validPasswords[0]);
        Password password2 = new Password(validPasswords[0]);
        assertEquals(password1.hashCode(), password2.hashCode());
    }

    /**
     * Tests whether two different objects return different hash codes.
     */
    @Test
    public void testHashCode_different() {
        Password password1 = new Password(validPasswords[0]);
        Password password2 = new Password(validPasswords[1]);
        assertNotEquals(password1.hashCode(), password2.hashCode());
    }
}

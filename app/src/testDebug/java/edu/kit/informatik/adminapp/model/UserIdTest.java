package edu.kit.informatik.adminapp.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.kit.informatik.adminapp.model.resources.Errors;

/**
 * This class tests the {@link UserId} class.
 */
public class UserIdTest {
    private String[] validUserIds = {
            "1",
            "bashjbcsa ajhsbcs",
            "&(!&(//=`",
            "as",
            ""
    };
    private String[] invalidUserIds = {
            "$",
            null,
            "asbj$HBhjbsa",
            "$bas",
            "2678$"
    };

    /**
     * Tests whether valid UserIds can be created.
     */
    @Test
    public void testValidUserId() {
        for (String validUserId : validUserIds) {
            new UserId(validUserId);
        }
    }

    /**
     * Tests whether invalid UserIds can be created.
     */
    @Test
    public void testInvalidUserId() {
        for (String invalidUserId : invalidUserIds) {
            Exception e = assertThrows(IllegalArgumentException.class,
                    () -> new UserId(invalidUserId));
            assertEquals(String.format(Errors.REGEX_NOT_MATCHED, invalidUserId,
                    UserId.REGEX_USER_ID), e.getMessage());
        }
    }

    /**
     * Tests the toString method.
     */
    @Test
    public void testToString() {
        for (String validUserId : validUserIds) {
            UserId userId = new UserId(validUserId);
            assertEquals(validUserId, userId.toString());
        }
    }

    /**
     * Tests the equals method for reflexivity.
     */
    @Test
    public void testEquals_self() {
        UserId userId1 = new UserId(validUserIds[0]);
        assertTrue(userId1.equals(userId1));
    }

    /**
     * Tests whether the equals method returns true for two identical objects.
     */
    @Test
    public void testEquals_same() {
        UserId userId1 = new UserId(validUserIds[0]);
        UserId userId2 = new UserId(validUserIds[0]);
        assertTrue(userId1.equals(userId2));
        assertTrue(userId2.equals(userId1));
    }

    /**
     * Tests whether the equals method returns false for two different objects.
     */
    @Test
    public void testEquals_different() {
        UserId userId1 = new UserId(validUserIds[0]);
        UserId userId2 = new UserId(validUserIds[1]);
        assertFalse(userId1.equals(userId2));
        assertFalse(userId2.equals(userId1));
    }

    /**
     * Tests the equals method with null.
     */
    @Test
    public void testEquals_null() {
        UserId userId1 = new UserId(validUserIds[0]);
        assertFalse(userId1.equals(null));
    }

    /**
     * Tests whether two identical objects produce the same hash code.
     */
    @Test
    public void testHashCode_same() {
        UserId userId1 = new UserId(validUserIds[0]);
        UserId userId2 = new UserId(validUserIds[0]);
        assertEquals(userId1.hashCode(), userId2.hashCode());
    }

    /**
     * Tests whether two different objects produce different hash codes.
     */
    @Test
    public void testHashCode_different() {
        UserId userId1 = new UserId(validUserIds[0]);
        UserId userId2 = new UserId(validUserIds[1]);
        assertNotEquals(userId1.hashCode(), userId2.hashCode());
    }
}

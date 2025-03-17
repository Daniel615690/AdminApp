package edu.kit.informatik.adminapp.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.kit.informatik.adminapp.model.resources.Errors;

/**
 * This class tests the {@link TokenId} class.
 */
public class TokenIdTest {
    private String[] validTokenIds = {
            "aksbasd",
            "&&!&/&/!)/&((=``",
            "21638712",
            "ASsahbda71263uas",
            "asiub 12n /(&"
    };
    private String[] invalidTokenIds = {
            null,
            "kashbc$asu",
            "$ashb",
            "(!/$",
            "$"
    };

    /**
     * Tests whether valid TokenIds can be created.
     */
    @Test
    public void testValidTokenId() {
        for (String validTokenId : validTokenIds) {
            new TokenId(validTokenId);
        }
    }

    /**
     * Tests whether invalid TokenIds throw an exception.
     */
    @Test
    public void testInvalidTokenId() {
        for (String invalidTokenId : invalidTokenIds) {
            Exception e = assertThrows(IllegalArgumentException.class,
                    () -> new TokenId(invalidTokenId));
            assertEquals(String.format(Errors.REGEX_NOT_MATCHED, invalidTokenId,
                    TokenId.REGEX_TOKEN_ID), e.getMessage());
        }
    }

    /**
     * Tests the toString method.
     */
    @Test
    public void testToString() {
        for (String validTokenId : validTokenIds) {
            TokenId tokenId = new TokenId(validTokenId);
            assertEquals(validTokenId, tokenId.toString());
        }
    }

    /**
     * Tests the equals method for reflexivity.
     */
    @Test
    public void testEquals_self() {
        TokenId tokenId1 = new TokenId(validTokenIds[0]);
        assertTrue(tokenId1.equals(tokenId1));
    }

    /**
     * Tests the equals method for two identical objects.
     */
    @Test
    public void testEquals_same() {
        TokenId tokenId1 = new TokenId(validTokenIds[0]);
        TokenId tokenId2 = new TokenId(validTokenIds[0]);
        assertTrue(tokenId1.equals(tokenId2));
        assertTrue(tokenId2.equals(tokenId1));
    }

    /**
     * Tests the equals method for two different objects.
     */
    @Test
    public void testEquals_different() {
        TokenId tokenId1 = new TokenId(validTokenIds[0]);
        TokenId tokenId2 = new TokenId(validTokenIds[1]);
        assertFalse(tokenId1.equals(tokenId2));
        assertFalse(tokenId2.equals(tokenId1));
    }

    /**
     * Tests the equals method with null.
     */
    @Test
    public void testEquals_null() {
        TokenId tokenId1 = new TokenId(validTokenIds[0]);
        assertFalse(tokenId1.equals(null));
    }

    /**
     * Tests whether two identical objects return the same hash code.
     */
    @Test
    public void testHashCode_same() {
        TokenId tokenId1 = new TokenId(validTokenIds[0]);
        TokenId tokenId2 = new TokenId(validTokenIds[0]);
        assertEquals(tokenId1.hashCode(), tokenId2.hashCode());
    }

    /**
     * Tests whether two different objects return different hash codes.
     */
    @Test
    public void testHashCode_different() {
        TokenId tokenId1 = new TokenId(validTokenIds[0]);
        TokenId tokenId2 = new TokenId(validTokenIds[1]);
        assertNotEquals(tokenId1.hashCode(), tokenId2.hashCode());
    }
}

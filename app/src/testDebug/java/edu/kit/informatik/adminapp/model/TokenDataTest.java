package edu.kit.informatik.adminapp.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the {@link TokenData} class.
 */
public class TokenDataTest {
    private String[] tokenDataString = {
            "238175612",
            "!&(/)(!=(/!",
            "Data",
            "ASh HAGSah  ahsgd7aa7 ,",
            "HAGG aaagAg ",
            TokenData.NO_DATA
    };
    private TokenData[] tokenData;
    private TokenData[] tokenDataCopy;

    /**
     * Initializes token data for testing.
     */
    @Before
    public void setUp() {
        this.tokenData = new TokenData[this.tokenDataString.length];
        this.tokenDataCopy = new TokenData[this.tokenDataString.length];
        for (int i = 0; i < tokenData.length - 1; i++) {
            this.tokenData[i] = new TokenData(this.tokenDataString[i]);
            this.tokenDataCopy[i] = new TokenData(this.tokenDataString[i]);
        }
        this.tokenData[this.tokenData.length - 1] = new TokenData();
        this.tokenDataCopy[this.tokenDataCopy.length - 1] = new TokenData();
    }

    /**
     * Tests the getData() method.
     */
    @Test
    public void testGetData() {
        for (int i = 0; i < this.tokenData.length; i++) {
            assertEquals(this.tokenDataString[i], this.tokenData[i].getData());
        }
    }

    /**
     * Tests the equals method for reflexivity.
     */
    @Test
    public void testEquals_self() {
        for (TokenData data : this.tokenData) {
            assertTrue(data.equals(data));
        }
    }

    /**
     * Tests the equals method for identical objects.
     */
    @Test
    public void testEquals_same() {
        for (int i = 0; i < this.tokenData.length; i++) {
            assertTrue(this.tokenData[i].equals(this.tokenDataCopy[i]));
        }
    }

    /**
     * Tests the equals method for different objects.
     */
    @Test
    public void testEquals_different() {
        for (int i = 0; i < this.tokenData.length; i++) {
            for (int j = 0; j < this.tokenData.length; j++) {
                if (i != j) {
                    assertFalse(this.tokenData[i].equals(this.tokenDataCopy[j]));
                }
            }
        }
    }

    /**
     * Tests the equals method with {@code null}.
     */
    @Test
    public void testEquals_null() {
        for (TokenData data : this.tokenData) {
            assertFalse(data.equals(null));
        }
    }

    /**
     * Tests the hashCode method for identical objects.
     */
    @Test
    public void testHashCode_same() {
        for (int i = 0; i < this.tokenData.length; i++) {
            assertEquals(this.tokenData[i].hashCode(), this.tokenDataCopy[i].hashCode());
        }
    }

    /**
     * Tests the hashCode method for different objects.
     */
    @Test
    public void testHashCode_different() {
        for (int i = 0; i < this.tokenData.length; i++) {
            for (int j = 0; j < this.tokenData.length; j++) {
                if (i != j) {
                    assertNotEquals(this.tokenData[i].hashCode(), this.tokenDataCopy[j].hashCode());
                }
            }
        }
    }

    /**
     * Tests the toString method.
     */
    @Test
    public void testToString() {
        for (int i = 0; i < this.tokenData.length; i++) {
            assertEquals(this.tokenDataString[i], this.tokenData[i].toString());
        }
    }
}

package edu.kit.informatik.adminapp.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * This class tests the {@link Attribute} class.
 */
public class AttributeTest {
    private Attribute attribute1;
    private Attribute attribute2;
    private String key1 = "key1";
    private String value1 = "value1";
    private String key2 = "key2";
    private String value2 = "value2";

    /**
     * Tests the getKey method for an attribute with a {@code null} key.
     */
    @Test
    public void testGetKey_null() {
        this.attribute1 = new Attribute(null, null);
        assertNull(attribute1.getKey());
    }

    /**
     * Tests the getKey method for an attribute with an empty key.
     */
    @Test
    public void testGetKey_empty() {
        this.attribute1 = new Attribute("", "");
        assertEquals("", this.attribute1.getKey());
    }

    /**
     * Tests the getKey method for an attribute with a non-empty key.
     */
    @Test
    public void testGetKey_notEmpty() {
        this.attribute1 = new Attribute(key1, value1);
        assertEquals(key1, this.attribute1.getKey());
    }

    /**
     * Tests the getValue method for an attribute with a {@code null} key.
     */
    @Test
    public void testGetValue_null() {
        this.attribute1 = new Attribute(null, null);
        assertNull(attribute1.getValue());
    }

    /**
     * Tests the getValue method for an attribute with an empty key.
     */
    @Test
    public void testGetValue_empty() {
        this.attribute1 = new Attribute("", "");
        assertEquals("", this.attribute1.getValue());
    }

    /**
     * Tests the getValue method for an attribute with a non-empty key.
     */
    @Test
    public void testGetValue_notEmpty() {
        this.attribute1 = new Attribute(key1, value1);
        assertEquals(value1, this.attribute1.getValue());
    }

    /**
     * Tests the equals method for reflexivity.
     */
    @Test
    public void testEquals_self() {
        attribute1 = new Attribute(key1, value1);
        assertTrue(attribute1.equals(attribute1));
    }

    /**
     * Tests the equals method for null.
     */
    @Test
    public void testEquals_null() {
        attribute1 = new Attribute(key1, value1);
        assertFalse(attribute1.equals(null));
    }

    /**
     * Tests the equals method for different objects.
     */
    @Test
    public void testEquals_different() {
        attribute1 = new Attribute(key1, value1);
        attribute2 = new Attribute(key2, value2);
        assertFalse(attribute1.equals(attribute2));
        assertFalse(attribute2.equals(attribute1));
    }

    /**
     * Tests the hashCode method.
     */
    @Test
    public void testHashCode() {
        attribute1 = new Attribute(key1, value1);
        attribute2 = new Attribute(key1, value1);
        assertEquals(attribute1.hashCode(), attribute2.hashCode());
    }
}

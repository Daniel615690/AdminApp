package edu.kit.informatik.adminapp.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.kit.informatik.adminapp.model.resources.Errors;

/**
 * This class tests the {@link Hostname} class.
 */
public class HostnameTest {
    private String[] validHostnames = {
            "192.168.111.111",
            "100.com",
            "hs.dd.com",
            "192.168.10.109",
            "127.0.1.1"
    };
    private String[] invalidHostnames = {
            null,
            "",
            "0123456789",
            "IP-REGEX",
            "Sample",
            "foo@demo.net",
            "bar.ba@test.co.uk",
            "256.168.111.1",
            "0.0.0.999",
            "1::1",
            "http://foo.co.uk/>><"
    };

    /**
     * Checks whether an exception is thrown when creating hostnames with invalid parameters.
     */
    @Test
    public void testInvalidHostname() {
        for (String invalidHostname : invalidHostnames) {
            Exception e = assertThrows(IllegalArgumentException.class,
                    () -> new Hostname(invalidHostname));
            assertEquals(String.format(Errors.REGEX_NOT_MATCHED, invalidHostname,
                    Hostname.REGEX_HOSTNAME), e.getMessage());
        }
    }

    /**
     * Checks whether no exception is thrown when creating hostnames with valid parameters.
     */
    @Test
    public void testValidHostname() {
        for (String validHostname : validHostnames) {
            new Hostname(validHostname);
        }
    }

    /**
     * Tests the toString method.
     */
    @Test
    public void testToString() {
        for (String validHostname : validHostnames) {
            Hostname hostname = new Hostname(validHostname);
            assertEquals(validHostname, hostname.toString());
        }
    }

    /**
     * Tests the equals method for reflexivity.
     */
    @Test
    public void testEquals_self() {
        Hostname hostname1 = new Hostname(validHostnames[0]);
        assertTrue(hostname1.equals(hostname1));
    }

    /**
     * Tests the equals method for two identical objects.
     */
    @Test
    public void equalsTest_same() {
        Hostname hostname1 = new Hostname(validHostnames[0]);
        Hostname hostname2 = new Hostname(validHostnames[0]);
        assertTrue(hostname1.equals(hostname2));
        assertTrue(hostname2.equals(hostname1));
    }

    /**
     * Tests the equals method for a null object.
     */
    @Test
    public void equalsTest_null() {
        Hostname hostname1 = new Hostname(validHostnames[0]);
        assertFalse(hostname1.equals(null));
    }

    /**
     * Tests the equals method for two different objects.
     */
    @Test
    public void equalsTest_notEquals() {
        Hostname hostname1 = new Hostname(validHostnames[0]);
        Hostname hostname2 = new Hostname(validHostnames[1]);
        assertFalse(hostname1.equals(hostname2));
        assertFalse(hostname2.equals(hostname1));
    }

    /**
     * Tests the hashCode method.
     */
    @Test
    public void testHashCode() {
        Hostname hostname1 = new Hostname(validHostnames[0]);
        Hostname hostname2 = new Hostname(validHostnames[0]);
        assertEquals(hostname1.hashCode(), hostname2.hashCode());
    }
}

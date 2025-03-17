package edu.kit.informatik.adminapp.model;

import java.util.Objects;

/**
 * This class represents an attribute in a database.
 * An attribute consists of a key and an associated value.
 *
 * @author Daniel Luckey
 * @version 1.0
 */
public class Attribute {
    private final String key;
    private String value;

    /**
     * Creates a new attribute.
     *
     * @param key   the key
     * @param value the value
     */
    public Attribute(final String key, final String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Returns the key.
     *
     * @return  the key
     */
    public String getKey() {
        return this.key;
    }

    /**
     * Returns the value.
     *
     * @return  the value
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Sets the value.
     *
     * @param value the new value
     */
    public void setValue(final String value) {
        this.value = value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Attribute attribute = (Attribute) o;
        return Objects.equals(getKey(), attribute.getKey())
                && Objects.equals(getValue(), attribute.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey(), getValue());
    }
}

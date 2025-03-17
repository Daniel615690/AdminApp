package edu.kit.informatik.adminapp.controller;

import java.security.NoSuchAlgorithmException;

/**
 * This interface defines the interface for a hash function.
 *
 * @author  Daniel Luckey
 * @version 1.0
 */
public interface HashFunction {
    /**
     * Returns the hashed value of a string.
     *
     * @param originalString    the string to be hashed
     * @return  the hash value of {@code originalString}
     * @throws NoSuchAlgorithmException if this algorithm is not available
     * on the used platform.
     */
    String hash(final String originalString) throws NoSuchAlgorithmException;
}

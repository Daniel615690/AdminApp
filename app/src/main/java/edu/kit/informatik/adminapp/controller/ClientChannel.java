package edu.kit.informatik.adminapp.controller;

import java.io.IOException;

/**
 * This interface describes a client-side connection through which data can be transmitted.
 *
 * @author Daniel Luckey
 * @version 1.0
 */
public interface ClientChannel extends AutoCloseable {
    /**
     * Establishes a new connection to a server.
     * If a connection already exists, a new connection is created,
     * and the old connection is not closed.
     *
     * @throws IOException  if an error occurs while establishing the connection
     */
    void connect() throws IOException;

    /**
     * Returns whether a connection to the server exists.
     *
     * @return  {@code true} if a connection to the server exists
     */
    boolean isConnected();

    /**
     * Sends a message to the server and returns the response from the server.
     *
     * @param message   the message
     * @return  the response from the server
     * @throws IOException  if no connection to the server exists, or
     *                      if an error occurs while sending
     */
    String send(final String message) throws IOException;

    /**
     * Closes the connection to the server.
     *
     * @throws IOException  if an error occurs while closing the connection
     */
    void close() throws IOException;

    /**
     * Returns whether {@link #close()} was successfully executed.
     *
     * @return  {@code true} if {@link #close()} was successfully executed
     */
    boolean isClosed();
}

package edu.kit.informatik.adminapp.controller.server;

import android.os.Parcelable;

import java.io.IOException;
import java.util.Collection;

import edu.kit.informatik.adminapp.model.Attribute;
import edu.kit.informatik.adminapp.model.Password;
import edu.kit.informatik.adminapp.model.Token;
import edu.kit.informatik.adminapp.model.User;
import edu.kit.informatik.adminapp.model.UserId;

/**
 * This interface defines a communication interface with a server.
 *
 * @author Daniel Luckey
 * @version 1.0
 */
public interface ServerAdapter extends AutoCloseable, Parcelable {
    /**
     * Sets a user ID for accessing the server.
     *
     * @param uid   the user ID
     */
    void set(UserId uid);

    /**
     * Sets a password for accessing the server.
     *
     * @param password  the password
     */
    void set(Password password);

    /**
     * Establishes a connection to the server.
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

    /**
     * Checks whether access to the server is possible with the user ID and password
     * of this PiAdapter. {@link #connect()} must have been successfully executed.
     *
     * @return  {@code true} if access to the server is possible, otherwise {@code false}
     * @throws IOException  if an error occurs while checking access
     */
    boolean isAuthenticated() throws IOException;

    /**
     * Searches every entry below the specified distinguished name, as well as the entry itself,
     * for the given attributes. Returns a collection of users that meet all attributes.
     * If no access rights to the server exist, an empty collection is returned.
     * {@link #connect()} must have been successfully executed.
     *
     * @param base          the distinguished name from which the search should be conducted
     * @param attributes    the attributes
     * @return              a collection of users that meet all attributes
     * @throws IOException if an error occurs during the search
     */
    Collection<User> search(
            final String base, final Attribute... attributes) throws IOException;

    /**
     * Adds a token to the user on the server.
     * If no access rights to the server exist, nothing is done.
     * {@link #connect()} must have been successfully executed.
     *
     * @param user  the user
     * @param token the token
     * @throws IOException  if an error occurs while adding the token
     */
    void addToken(User user, Token token) throws IOException;

    /**
     * Removes a token from the specified user.
     * Does not remove a token if the user does not own it.
     * If no access rights to the server exist, nothing is done.
     * {@link #connect()} must have been successfully executed.
     *
     * @param token the token
     * @param user  the user
     * @throws IOException  if an error occurs while removing the token
     */
    void removeToken(User user, Token token) throws IOException;

    /**
     * Removes all tokens from the user.
     * If no access rights to the server exist, nothing is done.
     * {@link #connect()} must have been successfully executed.
     *
     * @param user  the user
     * @throws IOException  if an error occurs while removing the tokens
     */
    void removeAllTokens(User user) throws IOException;
}

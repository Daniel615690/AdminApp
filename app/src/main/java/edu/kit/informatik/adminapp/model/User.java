package edu.kit.informatik.adminapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;

/**
 * This class represents a user who can own tokens.
 *
 * @author Daniel Luckey
 * @version 1.0
 */
public class User implements Parcelable {
    /**
     * Creates instances of the class.
     */
    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(final Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(final int size) {
            return new User[size];
        }
    };

    private final Name name;
    private final UserId uid;
    private Collection<Token> tokens;

    /**
     * Creates a new user.
     *
     * @param name  the name of the user
     * @param uid   the ID of the user
     * @param tokens the tokens of the user
     */
    public User(final Name name, final UserId uid, final Token... tokens) {
        this.name = name;
        this.uid = uid;
        this.tokens = new HashSet<>(Arrays.asList(tokens));
    }

    private User(final Parcel in) {
        this(
                in.readParcelable(Name.class.getClassLoader()),
                in.readParcelable(Name.class.getClassLoader()),
                in.createTypedArrayList(Token.CREATOR).toArray(new Token[0])
        );
    }

    /**
     * Returns the name of the user.
     *
     * @return  the name of the user
     */
    public Name getName() {
        return this.name;
    }

    /**
     * Returns the ID of the user.
     *
     * @return  the ID of the user
     */
    public UserId getId() {
        return this.uid;
    }

    /**
     * Returns the user's tokens as an unmodifiable collection.
     *
     * @return  the user's tokens
     */
    public Collection<Token> getTokens() {
        return Collections.unmodifiableCollection(this.tokens);
    }

    /**
     * Adds a new token to the user.
     *
     * @param token the new token
     */
    public void add(final Token token) {
        this.tokens.add(token);
    }

    /**
     * Removes a token from the user.
     * Does not remove a token if the user does not own it.
     *
     * @param token the token to be removed
     */
    public void remove(Token token) {
        this.tokens.remove(token);
    }

    /**
     * Removes all tokens from this user.
     */
    public void removeAllTokens() {
        this.tokens.clear();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int flag) {
        parcel.writeParcelable(this.name, flag);
        parcel.writeParcelable(this.uid, flag);
        parcel.writeTypedList(new ArrayList<>(this.tokens));
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final User user = (User) o;
        return Objects.equals(getName(), user.getName()) && Objects.equals(uid, user.uid)
                && equals(getTokens(), user.getTokens());
    }

    private <T> boolean equals(Collection<T> col1, Collection<T> col2) {
        if (col1 == null && col2 != null) return true;
        if (col1 == null || col2 == null) return false;

        for (T t : col1) {
            if (!col2.contains(t)) return false;
        }
        for (T t : col2) {
            if (!col1.contains(t)) return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), uid, this.tokens);
    }

    @Override
    public String toString() {
        return this.name.toString();
    }
}

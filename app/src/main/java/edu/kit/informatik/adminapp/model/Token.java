package edu.kit.informatik.adminapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

/**
 * This class represents a token with an ID, on which data can be stored.
 *
 * @author Daniel Luckey
 * @version 1.0
 */
public class Token implements Parcelable {
    /**
     * Creates instances of the class.
     */
    public static final Creator<Token> CREATOR = new Creator<Token>() {
        @Override
        public Token createFromParcel(final Parcel in) {
            return new Token(in);
        }

        @Override
        public Token[] newArray(final int size) {
            return new Token[size];
        }
    };

    private final TokenId id;
    private final TokenData data;

    /**
     * Creates a new token.
     *
     * @param id    the ID of the token, must not be {@code null}
     * @param data  the data of the token, must not be {@code null}
     */
    public Token(final TokenId id, final TokenData data) {
        if (id == null || data == null) {
            throw new NullPointerException();
        }

        this.id = id;
        this.data = data;
    }

    /**
     * Creates a new token without data.
     *
     * @param id   the ID of the token, must not be {@code null}
     */
    public Token(final TokenId id) {
        this(id, new TokenData());
    }

    private Token(final Parcel in) {
        this(in.readParcelable(TokenId.class.getClassLoader()),
                in.readParcelable(TokenData.class.getClassLoader()));
    }

    /**
     * Returns the token ID.
     *
     * @return  the token ID
     */
    public TokenId getId() {
        return this.id;
    }

    /**
     * Returns the data of this token.
     *
     * @return  the data of this token
     */
    public TokenData getData() {
        return this.data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int flag) {
        parcel.writeParcelable(this.id, flag);
        parcel.writeParcelable(this.data, flag);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Token token = (Token) o;
        return id.equals(token.id) && getData().equals(token.getData());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getData());
    }
}

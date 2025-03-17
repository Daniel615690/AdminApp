package edu.kit.informatik.adminapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

import edu.kit.informatik.adminapp.model.resources.Errors;

/**
 * This class represents the ID of a token.
 *
 * @author Daniel Luckey
 * @version 1.0
 */
public class TokenId implements Parcelable {
    /** Creates instances of the class. */
    public static final Creator<TokenId> CREATOR = new Creator<TokenId>() {
        @Override
        public TokenId createFromParcel(final Parcel in) {
            return new TokenId(in);
        }

        @Override
        public TokenId[] newArray(final int size) {
            return new TokenId[size];
        }
    };
    /** The regex that the token ID must conform to. */
    public static final String REGEX_TOKEN_ID = "[^$]*";

    private final String id;

    /**
     * Creates a new token ID and checks whether it conforms to the {@link #REGEX_TOKEN_ID regex}.
     *
     * @param id    the ID of the token
     * @throws IllegalArgumentException if {@code id} does not conform to the {@link #REGEX_TOKEN_ID
     * regex}
     */
    public TokenId(final String id) {
        if (id == null || !id.matches(REGEX_TOKEN_ID)) {
            throw new IllegalArgumentException(
                    String.format(Errors.REGEX_NOT_MATCHED, id, REGEX_TOKEN_ID));
        }
        this.id = id;
    }

    private TokenId(final Parcel in) {
        this(in.readString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int flag) {
        parcel.writeString(this.id);
    }

    @Override
    public String toString() {
        return this.id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final TokenId tokenId = (TokenId) o;
        return id.equals(tokenId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

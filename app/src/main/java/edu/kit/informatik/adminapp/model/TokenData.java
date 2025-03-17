package edu.kit.informatik.adminapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

import edu.kit.informatik.adminapp.model.resources.Errors;

/**
 * This class represents the data of a token.
 */
public class TokenData implements Parcelable {
    /** The regex that the data must conform to. */
    public static final String REGEX_TOKEN_DATA = ".*";
    /** The data for an empty token. */
    public static final String NO_DATA = "";
    /** Creates instances of the class. */
    public static final Creator<TokenData> CREATOR = new Creator<TokenData>() {
        @Override
        public TokenData createFromParcel(Parcel in) {
            return new TokenData(in);
        }

        @Override
        public TokenData[] newArray(int size) {
            return new TokenData[size];
        }
    };

    private final String data;

    /**
     * Creates a new TokenData object with data and checks whether {@code data} conforms
     * to the {@link #REGEX_TOKEN_DATA regex}.
     *
     * @param data  the data of the token
     * @throws IllegalArgumentException if {@code data} does not conform to
     * the {@link #REGEX_TOKEN_DATA regex}
     */
    public TokenData(final String data) {
        if (data == null || !data.matches(REGEX_TOKEN_DATA)) {
            throw new IllegalArgumentException(
                    String.format(Errors.REGEX_NOT_MATCHED, data, REGEX_TOKEN_DATA));
        }
        this.data = data;
    }

    /**
     * Creates a TokenData object without data.
     */
    public TokenData() {
        this(NO_DATA);
    }

    private TokenData(Parcel in) {
        this(in.readString());
    }

    /**
     * Returns the data of the object.
     *
     * @return the data of the object, {@link #NO_DATA} if the token has no data
     */
    public String getData() {
        return data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int i) {
        parcel.writeString(this.data);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final TokenData tokenData = (TokenData) o;
        return data.equals(tokenData.data);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(data);
    }

    @Override
    public String toString() {
        return this.data;
    }
}

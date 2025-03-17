package edu.kit.informatik.adminapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

import edu.kit.informatik.adminapp.model.resources.Errors;


/**
 * This class represents a password.
 *
 * @author Daniel Luckey
 * @version 1.0
 */
public class Password implements Parcelable {
    /**
     * Creates instances of the class.
     */
    public static final Creator<Password> CREATOR = new Creator<Password>() {
        @Override
        public Password createFromParcel(final Parcel in) {
            return new Password(in);
        }

        @Override
        public Password[] newArray(final int size) {
            return new Password[size];
        }
    };
    /** Regex that the password must conform to. */
    public static final String REGEX_PASSWORD = "[^$]*";

    private final String password;

    /**
     * Creates a new password and checks whether it conforms to the {@link #REGEX_PASSWORD regex}.
     *
     * @param password  the password
     * @throws IllegalArgumentException if {@code password} does not conform to
     * the {@link #REGEX_PASSWORD regex}
     */
    public Password(final String password) {
        if (password == null || !password.matches(REGEX_PASSWORD)) {
            throw new IllegalArgumentException(
                    String.format(Errors.REGEX_NOT_MATCHED, password, REGEX_PASSWORD));
        }
        this.password = password;
    }

    private Password(final Parcel in) {
        this(in.readString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int flag) {
        parcel.writeString(this.password);
    }

    @Override
    public String toString() {
        return this.password;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Password password1 = (Password) o;
        return password.equals(password1.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password);
    }
}

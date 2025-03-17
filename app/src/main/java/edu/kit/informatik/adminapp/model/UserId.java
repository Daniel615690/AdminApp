package edu.kit.informatik.adminapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

import edu.kit.informatik.adminapp.model.resources.Errors;

/**
 * This class represents the ID of a user.
 *
 * @author Daniel Luckey
 * @version 1.0
 */
public class UserId implements Parcelable {
    /**
     * The regex that the user ID must conform to.
     */
    public static final String REGEX_USER_ID = "[^$]*";
    /**
     * Creates instances of the class.
     */
    public static final Creator<UserId> CREATOR = new Creator<UserId>() {
        @Override
        public UserId createFromParcel(final Parcel in) {
            return new UserId(in);
        }

        @Override
        public UserId[] newArray(final int size) {
            return new UserId[size];
        }
    };

    private final String uid;

    /**
     * Creates a new user ID and checks whether it conforms to the {@link #REGEX_USER_ID regex}.
     *
     * @param uid   the user ID
     * @throws IllegalArgumentException if {@code uid} does not conform to the {@link #REGEX_USER_ID
     * regex}
     */
    public UserId(final String uid) {
        if (uid == null || !uid.matches(REGEX_USER_ID)) {
            throw new IllegalArgumentException(
                    String.format(Errors.REGEX_NOT_MATCHED, uid, REGEX_USER_ID));
        }
        this.uid = uid;
    }

    private UserId(final Parcel in) {
        this(in.readString());
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(this.uid);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return this.uid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UserId userId = (UserId) o;
        return uid.equals(userId.uid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid);
    }
}

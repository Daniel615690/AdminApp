package edu.kit.informatik.adminapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

import edu.kit.informatik.adminapp.model.resources.Errors;

/**
 * This class represents a user's name.
 *
 * @author  Daniel Luckey
 * @version 1.0
 */
public class Name implements Parcelable {
    /** Regex that the name must conform to. */
    public static final String REGEX_NAME = "[^$]*";
    /** Creates instances of the class. */
    public static final Creator<Name> CREATOR = new Creator<Name>() {
        @Override
        public Name createFromParcel(final Parcel in) {
            return new Name(in);
        }

        @Override
        public Name[] newArray(final int size) {
            return new Name[size];
        }
    };

    private String name;

    /**
     * Creates a new name and checks whether it conforms to the {@link #REGEX_NAME regex}.
     *
     * @param name  the name
     * @throws IllegalArgumentException if {@code name} does not conform to the {@link #REGEX_NAME
     * regex}
     */
    public Name(final String name) {
        if (name == null || !name.matches(REGEX_NAME)) {
            throw new IllegalArgumentException(
                    String.format(Errors.REGEX_NOT_MATCHED, name, REGEX_NAME));
        }
        this.name = name;
    }

    private Name(final Parcel in) {
        this(in.readString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int flag) {
        parcel.writeString(this.name);
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Name name1 = (Name) o;
        return name.equals(name1.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

package edu.kit.informatik.adminapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

import edu.kit.informatik.adminapp.model.resources.Errors;

/**
 * This class represents a non-negative duration in milliseconds.
 */
public class Milliseconds implements Parcelable {
    /** Creates instances of the class. */
    public static final Creator<Milliseconds> CREATOR = new Creator<Milliseconds>() {
        @Override
        public Milliseconds createFromParcel(Parcel in) {
            return new Milliseconds(in);
        }

        @Override
        public Milliseconds[] newArray(int size) {
            return new Milliseconds[size];
        }
    };

    private int milliseconds;

    /**
     * Creates a Milliseconds object.
     *
     * @param milliseconds  the milliseconds, must be &ge; 0
     * @throws IllegalArgumentException if {@code milliseconds} &lt; 0
     */
    public Milliseconds(final int milliseconds) {
        if (milliseconds < 0) {
            throw new IllegalArgumentException(String.format(Errors.NEGATIVE_NUMBER, milliseconds));
        }
        this.milliseconds = milliseconds;
    }

    private Milliseconds(Parcel in) {
        this(in.readInt());
    }

    /**
     * Returns the integer representation of this object.
     *
     * @return  the milliseconds as an integer
     */
    public int asInt() {
        return milliseconds;
    }

    @Override
    public String toString() {
        return String.valueOf(this.milliseconds);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Milliseconds that = (Milliseconds) o;
        return asInt() == that.asInt();
    }

    @Override
    public int hashCode() {
        return Objects.hash(asInt());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int i) {
        parcel.writeInt(this.milliseconds);
    }
}

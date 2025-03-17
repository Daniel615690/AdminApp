package edu.kit.informatik.adminapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

import edu.kit.informatik.adminapp.model.resources.Errors;

/**
 * This class represents a hostname within a network.
 *
 * @author Daniel Luckey
 * @version 1.0
 */
public class Hostname implements Parcelable {
    /**
     * Creates instances of the class.
     */
    public static final Creator<Hostname> CREATOR = new Creator<Hostname>() {
        @Override
        public Hostname createFromParcel(final Parcel in) {
            return new Hostname(in);
        }

        @Override
        public Hostname[] newArray(final int size) {
            return new Hostname[size];
        }
    };
    /**
     * REGEX that the hostname must conform to.
     */
    public static final String REGEX_HOSTNAME = "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.)" +
            "{3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$|^(([a-zA-Z0-9]|[a-zA-Z0-9]" +
            "[a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)+([A-Za-z]|[A-Za-z][A-Za-z0-9\\-]*[A-Za-z0-9])$";;

    private final String hostname;

    /**
     * Creates a new hostname and checks whether it conforms to the {@link #REGEX_HOSTNAME regex}.
     *
     * @param hostname  the hostname
     * @throws IllegalArgumentException if {@code hostname} does not conform to
     * the {@link #REGEX_HOSTNAME regex}
     */
    public Hostname(final String hostname) {
        if (hostname == null ||!hostname.matches(REGEX_HOSTNAME)) {
            throw new IllegalArgumentException(
                    String.format(Errors.REGEX_NOT_MATCHED, hostname, REGEX_HOSTNAME));
        }
        this.hostname = hostname;
    }

    private Hostname(final Parcel in) {
        this(in.readString());
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(this.hostname);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return this.hostname;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Hostname hostname1 = (Hostname) o;
        return hostname.equals(hostname1.hostname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hostname);
    }
}

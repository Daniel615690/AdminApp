package edu.kit.informatik.adminapp.model.resources;

import edu.kit.informatik.adminapp.model.User;
import edu.kit.informatik.adminapp.controller.server.ServerAdapter;

/**
 * This class contains IDs that can be used to identify extras
 * that are passed to an intent.
 *
 * @version 1.0
 * @author Daniel Luckey
 */
public final class Extras {
    private Extras() {}

    /** Extra for a {@link ServerAdapter}. */
    public static final String EXTRA_SERVER_ADAPTER = "edu.kit.informatik.EXTRA_SERVER";
    /** Extra for a {@link User}. */
    public static final String EXTRA_USER = "edu.kit.informatik.EXTRA_USER";
}

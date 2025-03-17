package edu.kit.informatik.adminapp.model.resources;

/**
 * This class contains error messages for classes that cannot access the local resources of the app.
 */
public final class Errors {
    private Errors() {}

    /** Error message for a negative parameter. Format includes the incorrect parameter. */
    public static final String NEGATIVE_NUMBER = "'%d' should be >= 0";
    /** Error message for a parameter that does not match the expected regex.
     * Format includes the parameter and the regex. */
    public static final String REGEX_NOT_MATCHED = "'%s' does not match '%s'";
    /** Error message for an incorrect number of parameters in a search.
     * Format includes the expected number of parameters. */
    public static final String WRONG_NUMBER_SEARCH_ARGUMENTS
            = "can only search for '%d' arguments";
}

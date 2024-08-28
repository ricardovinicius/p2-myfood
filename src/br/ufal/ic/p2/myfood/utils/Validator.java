package br.ufal.ic.p2.myfood.utils;

import java.util.regex.Pattern;

/**
 * Utility class that provides validation methods for common data types such as strings and emails.
 *
 * <p>This class contains static methods to check whether a string is null or empty,
 * and to validate the format of an email address based on a regular expression pattern.</p>
 *
 * @since 1.0
 */
public class Validator {
    /**
     * Checks if a given string is either {@code null} or empty.
     *
     * @param s the string to be checked.
     * @return {@code true} if the string is {@code null} or empty, {@code false} otherwise.
     */
    public static boolean isNullOrEmpty(final String s) {
        return s == null || s.isBlank();
    }

    /**
     * Validates whether the given string is a properly formatted email address.
     *
     * <p>The method first checks if the string is not {@code null} or empty, and then
     * applies a regular expression pattern to validate the email format.</p>
     *
     * <p>The pattern used for validation checks for the presence of characters
     * before and after the {@code @} symbol, but does not enforce strict domain validation.</p>
     *
     * @param s the string to be checked.
     * @return {@code true} if the string is a valid email address, {@code false} otherwise.
     */
    public static boolean isValidEmail(final String s) {
        if (isNullOrEmpty(s)) {
            return false;
        }

        String regexPattern = "^(.+)@(\\S+)$";

        return Pattern.compile(regexPattern)
                .matcher(s)
                .matches();
    }
}

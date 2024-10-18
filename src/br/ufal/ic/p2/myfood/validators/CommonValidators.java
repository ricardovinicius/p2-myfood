package br.ufal.ic.p2.myfood.validators;

import br.ufal.ic.p2.myfood.exceptions.InvalidIndexException;

import java.util.regex.Pattern;

/**
 * Utility class that provides validation methods for common data types such as strings and emails.
 *
 * <p>This class contains static methods to check whether a string is null or empty,
 * and to validate the format of an email address based on a regular expression pattern.</p>
 *
 * @since 1.0
 */
public class CommonValidators {
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

    /**
     * Parses the given string as an integer index.
     *
     * @param stringIndex the string to be parsed as an integer.
     * @return the parsed integer index.
     * @throws InvalidIndexException if the string cannot be parsed to an integer
     *                               or if the index is negative.
     */
    public static int parseIndex(String stringIndex) {
        int index;

        try {
            index = Integer.parseInt(stringIndex);
        } catch (RuntimeException e) {
            throw new InvalidIndexException("Indice invalido");
        }

        if (index < 0) {
            throw new InvalidIndexException("Indice invalido");
        }

        return index;
    }

    /**
     * Validates the format of the given time string.
     *
     * @param s the time string to be validated.
     * @return {@code true} if the string is null (according to specification),
     *         {@code false} if it is empty or does not match the time format (HH:mm).
     */
    public static boolean isValidTimeFormat(String s) {
        if (s == null) {
            return true; // Isso não faz sentido, mas eu preciso fazer isso por conta da especificação dos erros :|
        }

        if (s.isEmpty()) {
            return false;
        }

        String regexPattern = "^\\d{2}:\\d{2}$";

        return Pattern.compile(regexPattern)
                .matcher(s)
                .matches();
    }

    /**
     * Validates the time range defined by the start and end time strings.
     *
     * @param start the start time string in HH:mm format.
     * @param end   the end time string in HH:mm format.
     * @return {@code true} if the time range is valid, {@code false} otherwise.
     */
    public static boolean isValidTimeRange(String start, String end) {
        if (isNullOrEmpty(start) || isNullOrEmpty(end)) {
            return false;
        }

        int startInt = Integer.parseInt(start.replace(":", ""));
        int endInt = Integer.parseInt(end.replace(":", ""));

        if (startInt - endInt > 0) {
            return false;
        }

        if (startInt > 2399 || endInt > 2399) {
            return false;
        }

        return true;
    }
}

package br.ufal.ic.p2.myfood.exceptions;

/**
 * This exception is thrown when an invalid attribute is encountered.
 * <p>
 * Use this exception to indicate that an attribute provided to an operation
 * is invalid or does not meet the expected criteria.
 * </p>
 */
public class InvalidAttributeException extends RuntimeException {

    /**
     * Constructs a new {@code InvalidAttributeException} with the specified detail message.
     *
     * @param message the detail message explaining why the exception was thrown.
     */
    public InvalidAttributeException(String message) {
        super(message);
    }
}


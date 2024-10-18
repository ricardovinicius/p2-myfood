package br.ufal.ic.p2.myfood.exceptions.user;

/**
 * This exception is thrown when a username is invalid.
 * <p>
 * Use this exception to indicate that the provided username does not meet
 * the required criteria, such as format, length, or allowed characters.
 * </p>
 */
public class InvalidUserNameException extends RuntimeException {

    /**
     * Constructs a new {@code InvalidUserNameException} with the specified detail message.
     *
     * @param message the detail message explaining why the exception was thrown.
     */
    public InvalidUserNameException(String message) {
        super(message);
    }
}

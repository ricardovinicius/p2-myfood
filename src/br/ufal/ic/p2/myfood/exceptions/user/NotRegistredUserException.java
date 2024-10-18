package br.ufal.ic.p2.myfood.exceptions.user;

/**
 * This exception is thrown when an operation is attempted with a user who is not registered.
 * <p>
 * Use this exception to indicate that the specified action cannot be performed
 * because the user does not have a valid registration in the system.
 * </p>
 */
public class NotRegistredUserException extends RuntimeException {

    /**
     * Constructs a new {@code NotRegistredUserException} with the specified detail message.
     *
     * @param message the detail message explaining why the exception was thrown.
     */
    public NotRegistredUserException(String message) {
        super(message);
    }
}

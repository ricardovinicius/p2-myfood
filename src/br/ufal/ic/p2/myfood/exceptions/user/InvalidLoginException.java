package br.ufal.ic.p2.myfood.exceptions.user;

/**
 * This exception is thrown when a login attempt fails due to invalid credentials.
 * <p>
 * Use this exception to indicate that the provided username or password is incorrect
 * or that the login attempt cannot be processed for any other reason related to login.
 * </p>
 */
public class InvalidLoginException extends RuntimeException {

    /**
     * Constructs a new {@code InvalidLoginException} with the specified detail message.
     *
     * @param message the detail message explaining why the exception was thrown.
     */
    public InvalidLoginException(String message) {
        super(message);
    }
}

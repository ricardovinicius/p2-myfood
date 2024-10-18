package br.ufal.ic.p2.myfood.exceptions.company;

/**
 * This exception is thrown when an operation is attempted by a user who is not the owner.
 * <p>
 * Use this exception to indicate that a specific action cannot be performed
 * because the user does not have ownership rights over the resource.
 * </p>
 */
public class NotOwnerUserException extends RuntimeException {

    /**
     * Constructs a new {@code NotOwnerUserException} with the specified detail message.
     *
     * @param message the detail message explaining why the exception was thrown.
     */
    public NotOwnerUserException(String message) {
        super(message);
    }
}


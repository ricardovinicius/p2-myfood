package br.ufal.ic.p2.myfood.exceptions.user;

/**
 * This exception is thrown when an attempt is made to register an email
 * that is already in use.
 * <p>
 * Use this exception to indicate that the provided email address cannot be
 * registered because it is already associated with an existing account.
 * </p>
 */
public class AlreadyRegistredEmailException extends RuntimeException {

    /**
     * Constructs a new {@code AlreadyRegistredEmailException} with the specified detail message.
     *
     * @param message the detail message explaining why the exception was thrown.
     */
    public AlreadyRegistredEmailException(String message) {
        super(message);
    }
}


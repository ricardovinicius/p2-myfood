package br.ufal.ic.p2.myfood.exceptions.user;

/**
 * This exception is thrown when a user is not associated with any company.
 * <p>
 * Use this exception to indicate that an operation cannot proceed because
 * the user does not belong to any company in the system.
 * </p>
 */
public class NullCompanyUser extends RuntimeException {

    /**
     * Constructs a new {@code NullCompanyUser} with the specified detail message.
     *
     * @param message the detail message explaining why the exception was thrown.
     */
    public NullCompanyUser(String message) {
        super(message);
    }
}


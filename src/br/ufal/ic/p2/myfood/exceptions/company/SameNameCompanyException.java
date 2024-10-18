package br.ufal.ic.p2.myfood.exceptions.company;

/**
 * This exception is thrown when a company with the same name already exists.
 * <p>
 * Use this exception to indicate that a new company cannot be registered
 * because another company with the same name is already present in the system.
 * </p>
 */
public class SameNameCompanyException extends RuntimeException {

    /**
     * Constructs a new {@code SameNameCompanyException} with the specified detail message.
     *
     * @param message the detail message explaining why the exception was thrown.
     */
    public SameNameCompanyException(String message) {
        super(message);
    }
}


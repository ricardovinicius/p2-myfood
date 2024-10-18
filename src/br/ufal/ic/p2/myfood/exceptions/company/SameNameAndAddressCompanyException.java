package br.ufal.ic.p2.myfood.exceptions.company;

/**
 * This exception is thrown when a company with the same name and address already exists.
 * <p>
 * Use this exception to indicate that a new company cannot be registered because
 * another company with the same name and address is already present in the system.
 * </p>
 */
public class SameNameAndAddressCompanyException extends RuntimeException {

    /**
     * Constructs a new {@code SameNameAndAddressCompanyException} with the specified detail message.
     *
     * @param message the detail message explaining why the exception was thrown.
     */
    public SameNameAndAddressCompanyException(String message) {
        super(message);
    }
}


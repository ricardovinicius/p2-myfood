package br.ufal.ic.p2.myfood.exceptions.company;

/**
 * This exception is thrown when a company name is invalid.
 * <p>
 * Use this exception to indicate that the provided company name does not meet
 * the required criteria, such as format, length, or allowed characters.
 * </p>
 */
public class InvalidCompanyNameException extends RuntimeException {

    /**
     * Constructs a new {@code InvalidCompanyNameException} with the specified detail message.
     *
     * @param message the detail message explaining why the exception was thrown.
     */
    public InvalidCompanyNameException(String message) {
        super(message);
    }
}


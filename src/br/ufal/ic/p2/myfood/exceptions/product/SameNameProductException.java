package br.ufal.ic.p2.myfood.exceptions.product;

/**
 * This exception is thrown when a product with the same name already exists.
 * <p>
 * Use this exception to indicate that a new product cannot be added because
 * another product with the same name is already present in the system.
 * </p>
 */
public class SameNameProductException extends RuntimeException {

    /**
     * Constructs a new {@code SameNameProductException} with the specified detail message.
     *
     * @param message the detail message explaining why the exception was thrown.
     */
    public SameNameProductException(String message) {
        super(message);
    }
}


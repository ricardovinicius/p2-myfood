package br.ufal.ic.p2.myfood.exceptions;

/**
 * This exception is thrown when an invalid index is encountered.
 * <p>
 * Use this exception to indicate that an operation was attempted
 * with an index that is out of bounds or otherwise invalid.
 * </p>
 */
public class InvalidIndexException extends RuntimeException {

    /**
     * Constructs a new {@code InvalidIndexException} with the specified detail message.
     *
     * @param message the detail message explaining why the exception was thrown.
     */
    public InvalidIndexException(String message) {
        super(message);
    }
}


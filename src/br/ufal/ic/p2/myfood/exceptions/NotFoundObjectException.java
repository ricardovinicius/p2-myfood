package br.ufal.ic.p2.myfood.exceptions;

/**
 * This exception is thrown when an object is not found in the system.
 * <p>
 * It extends {@link RuntimeException}, which means it is an unchecked exception.
 * You can use this exception to signal that a specific object was expected but could not be found.
 * </p>
 *
 * @see RuntimeException
 */
public class NotFoundObjectException extends RuntimeException {

    /**
     * Constructs a new {@code NotFoundObjectException} with the specified detail message.
     *
     * @param message the detail message explaining why the exception was thrown.
     */
    public NotFoundObjectException(String message) {
        super(message);
    }
}

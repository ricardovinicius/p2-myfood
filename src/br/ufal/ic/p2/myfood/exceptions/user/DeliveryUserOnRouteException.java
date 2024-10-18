package br.ufal.ic.p2.myfood.exceptions.user;

/**
 * This exception is thrown when an operation is attempted with a delivery user
 * who is currently on a route.
 * <p>
 * Use this exception to indicate that the specified action cannot be performed
 * because the delivery user is actively engaged in a delivery route.
 * </p>
 */
public class DeliveryUserOnRouteException extends RuntimeException {

    /**
     * Constructs a new {@code DeliveryUserOnRouteException} with the specified detail message.
     *
     * @param message the detail message explaining why the exception was thrown.
     */
    public DeliveryUserOnRouteException(String message) {
        super(message);
    }
}

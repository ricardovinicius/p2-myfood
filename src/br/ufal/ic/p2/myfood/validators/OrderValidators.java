package br.ufal.ic.p2.myfood.validators;

import br.ufal.ic.p2.myfood.exceptions.company.NotOwnerUserException;
import br.ufal.ic.p2.myfood.exceptions.order.DoneOrderException;
import br.ufal.ic.p2.myfood.exceptions.order.OpenOrderException;
import br.ufal.ic.p2.myfood.models.Order;
import br.ufal.ic.p2.myfood.models.User;

/**
 * Validator class for order-related operations.
 */
public class OrderValidators {

    /**
     * Validates if the user can create an order based on their role.
     *
     * @param user the user attempting to create an order.
     * @throws NotOwnerUserException if the user is not an owner.
     */
    public static void userCanCreateOrder(User user) {
        if (!(user.getDtype().equals("owner"))) {
            throw new NotOwnerUserException("Usuario nao pode criar uma empresa");
        }
    }

    /**
     * Checks if the order is open.
     *
     * @param order the order to check.
     * @return true if the order is open, false otherwise.
     */
    public static boolean orderIsOpen(Order order) {
        return order.getStatus().equals("aberto");
    }

    /**
     * Validates if an order can be released based on its status.
     *
     * @param order the order to validate.
     * @throws DoneOrderException if the order is already released.
     * @throws OpenOrderException if the order is still open.
     */
    public static void orderCanBeReleased(Order order) {
        if (order.getStatus().equals("pronto")) {
            throw new DoneOrderException("Pedido ja liberado");
        }

        if (order.getStatus().equals("aberto")) {
            throw new OpenOrderException("Nao e possivel liberar um produto que nao esta sendo preparado");
        }
    }

    /**
     * Validates if an order can be delivered based on its status.
     *
     * @param order the order to validate.
     * @throws OpenOrderException if the order is not ready for delivery.
     */
    public static void orderCanBeDelivered(Order order) {
        if (!order.getStatus().equals("pronto")) {
            throw new OpenOrderException("Pedido nao esta pronto para entrega");
        }
    }
}

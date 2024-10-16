package br.ufal.ic.p2.myfood.validators;

import br.ufal.ic.p2.myfood.exceptions.company.NotOwnerUserException;
import br.ufal.ic.p2.myfood.models.Order;
import br.ufal.ic.p2.myfood.models.User;

public class OrderValidators {
    public static void userCanCreateOrder(User user) {
        if (!(user.getDtype().equals("owner"))) {
            throw new NotOwnerUserException("Usuario nao pode criar uma empresa");
        }
    }

    public static boolean orderIsOpen(Order order) {
        return order.getStatus().equals("aberto");
    }
}

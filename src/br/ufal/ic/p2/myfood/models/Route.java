package br.ufal.ic.p2.myfood.models;

import br.ufal.ic.p2.myfood.types.Persistent;

import java.util.Locale;

public class Route extends Persistent {
    private static int id_counter = 1;
    private Order order;
    private User delivery;
    private String destination;

    public Route() {}

    Route(Order order, User delivery, String destination) {
        this.id = id_counter++;
        this.order = order;
        this.delivery = delivery;
        this.destination = destination;
    }

    public static Route create(Order order, User delivery, String destination) {
        return new Route(order, delivery, destination);
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public User getDelivery() {
        return delivery;
    }

    public void setDelivery(User delivery) {
        this.delivery = delivery;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getAttribute(String attribute) {
        if (attribute.equals("cliente")) {
            return getOrder().getCustomer().getName();
        }

        if (attribute.equals("empresa")) {
            return getOrder().getCompany().getName();
        }

        if (attribute.equals("pedido")) {
            return String.valueOf(getOrder().getId());
        }

        if (attribute.equals("entregador")) {
            return getDelivery().getName();
        }

        if (attribute.equals("destino")) {
            return getDestination();
        }

        if (attribute.equals("produtos")) {
            return getOrder().getAttribute("produtos");
        }

        throw new IllegalArgumentException("Atributo nao existe");
    }
}

package br.ufal.ic.p2.myfood.managers;

import br.ufal.ic.p2.myfood.exceptions.NotFoundObjectException;
import br.ufal.ic.p2.myfood.models.Order;
import br.ufal.ic.p2.myfood.models.Route;
import br.ufal.ic.p2.myfood.models.User;
import br.ufal.ic.p2.myfood.repositories.OrderRepository;
import br.ufal.ic.p2.myfood.repositories.RouteRepository;
import br.ufal.ic.p2.myfood.repositories.UserRepository;
import br.ufal.ic.p2.myfood.validators.*;

import java.io.IOException;
import java.util.Optional;

public class RouteManager extends Manager {
    RouteRepository routeRepository = RouteRepository.getInstance();
    void cleanRepository() throws IOException {
        routeRepository.clean();
    }
    void saveRepository() throws IOException {
        routeRepository.save();
    }

    public int createRoute(int orderId, int deliveryId, String destination) {
        OrderRepository orderRepository = OrderRepository.getInstance();
        Order order = orderRepository.getById(orderId).get();

        UserRepository userRepository = UserRepository.getInstance();
        User user = userRepository.getById(deliveryId).get();

        OrderValidators.orderCanBeDelivered(order);
        RouteValidators.deliveryUserIsOnRoute(user);

        try {
            CompanyValidators.userCanDelivery(user);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Nao e um entregador valido"); // DRY: já existe uma validação anterior com mensagens diferentes
        }

        Route route = Route.create(order, user, destination);

        routeRepository.add(route);

        order.setStatus("entregando");

        return route.getId();
    }

    public String getRouteAttribute(int id, String attributeName) {
        if (CommonValidators.isNullOrEmpty(attributeName)) {
            throw new IllegalArgumentException("Atributo invalido");
        }

        return routeRepository.getById(id).get().getAttribute(attributeName);
    }

    public int getRouteId(int orderId) {
        Optional<Route> routeOptional = routeRepository.getByOrderId(orderId);

        if (routeOptional.isEmpty()) {
            throw new NotFoundObjectException("Nao existe entrega com esse id");
        }

        return routeOptional.get().getId();
    }

    public void setRouteDelivered(int routeId) {
        Optional<Route> routeOptional = routeRepository.getById(routeId);

        if (routeOptional.isEmpty()) {
            throw new NotFoundObjectException("Nao existe nada para ser entregue com esse id");
        }

        Route route = routeOptional.get();

        route.getOrder().setStatus("entregue");
    }
}

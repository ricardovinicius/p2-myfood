package br.ufal.ic.p2.myfood.validators;

import br.ufal.ic.p2.myfood.exceptions.user.DeliveryUserOnRouteException;
import br.ufal.ic.p2.myfood.models.Route;
import br.ufal.ic.p2.myfood.models.User;
import br.ufal.ic.p2.myfood.repositories.RouteRepository;

import java.util.List;

public class RouteValidators {
    public static void deliveryUserIsOnRoute(User user) {
        RouteRepository routeRepository = RouteRepository.getInstance();
        List<Route> userRoutes = routeRepository.list().stream().filter(route -> route.getDelivery().equals(user)).toList();

        if (userRoutes.stream().anyMatch(route -> route.getOrder().getStatus().equals("entregando"))) {
            throw new DeliveryUserOnRouteException("Entregador ainda em entrega");
        }
    }
}

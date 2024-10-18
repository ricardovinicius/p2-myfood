package br.ufal.ic.p2.myfood.repositories;

import br.ufal.ic.p2.myfood.models.Route;
import br.ufal.ic.p2.myfood.models.User;

import java.util.Optional;

public class RouteRepository extends Repository<Route> {
    private static RouteRepository instance;
    private RouteRepository() {
        super(Route.class);
    }

    // Method to access the singleton instance
    public static RouteRepository getInstance() {
        if (instance == null) {
            synchronized (RouteRepository.class) {
                if (instance == null) {
                    instance = new RouteRepository();
                }
            }
        }
        return instance;
    }

    public Optional<Route> getById(int id) {
        return list().stream().filter(u -> u.getId() == id).findFirst();
    }

    public Optional<Route> getByOrderId(int orderId) {
        return list().stream().filter(route -> route.getOrder().getId() == orderId).findFirst();
    }
}

package br.ufal.ic.p2.myfood.repositories;

import br.ufal.ic.p2.myfood.models.Order;

import java.util.List;
import java.util.Optional;

public class OrderRepository extends Repository<Order> {
    private static OrderRepository instance;
    private OrderRepository() {
        super(Order.class);
    }

    // Method to access the singleton instance
    public static OrderRepository getInstance() {
        if (instance == null) {
            synchronized (OrderRepository.class) {
                if (instance == null) {
                    instance = new OrderRepository();
                }
            }
        }
        return instance;
    }

    public Optional<Order> getById(int id) {
        return list().stream().filter(o -> o.getId() == id).findFirst();
    }

    public List<Order> listByCompanyId(int id) {
        return list().stream().filter(o -> o.getCompany().getId() == id).toList();
    }
}

package br.ufal.ic.p2.myfood.managers;

import br.ufal.ic.p2.myfood.exceptions.InvalidAttributeException;
import br.ufal.ic.p2.myfood.exceptions.NotFoundObjectException;
import br.ufal.ic.p2.myfood.exceptions.order.ClosedOrderException;
import br.ufal.ic.p2.myfood.exceptions.order.DuplicateOrderException;
import br.ufal.ic.p2.myfood.exceptions.order.OwnerCreateOrderException;
import br.ufal.ic.p2.myfood.models.Company;
import br.ufal.ic.p2.myfood.models.Order;
import br.ufal.ic.p2.myfood.models.Product;
import br.ufal.ic.p2.myfood.models.User;
import br.ufal.ic.p2.myfood.repositories.CompanyRepository;
import br.ufal.ic.p2.myfood.repositories.OrderRepository;
import br.ufal.ic.p2.myfood.repositories.ProductRepository;
import br.ufal.ic.p2.myfood.repositories.UserRepository;
import br.ufal.ic.p2.myfood.utils.Validators;
import br.ufal.ic.p2.myfood.validators.OrderValidators;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class OrderManager extends Manager {
    OrderRepository orderRepository = OrderRepository.getInstance();

    void cleanRepository() throws IOException {
        orderRepository.clean();
    }

    void saveRepository() throws IOException {
        orderRepository.save();
    }

    public int createOrder(int userId, int companyId) {
        UserRepository userRepository = UserRepository.getInstance();
        Optional<User> userOptional = userRepository.getById(userId);

        if (userOptional.isEmpty()) {
            throw new NotFoundObjectException("");
        }
        User user = userOptional.get();

        if (user.getDtype().equals("owner")) {
            throw new OwnerCreateOrderException("Dono de empresa nao pode fazer um pedido");
        }

        CompanyRepository companyRepository = CompanyRepository.getInstance();
        Optional<Company> companyOptional = companyRepository.getById(companyId);

        if (companyOptional.isEmpty()) {
            throw new RuntimeException();
        }
        Company company = companyOptional.get();

        List<Order> companyOrders = orderRepository.listByCompanyId(companyId);

        Optional<Order> existentOrder = companyOrders.stream()
                .filter(o -> o.getCustomer().getId() == userId)
                .filter(o -> o.getStatus().equals("aberto"))
                .findFirst();

        if (existentOrder.isPresent()) {
            throw new DuplicateOrderException("Nao e permitido ter dois pedidos em aberto para a mesma empresa");
        }

        Order order = Order.create(user, company);
        orderRepository.add(order);

        return order.getId();
    }

    public void addProductToOrder(int orderId, int productId) {
        Optional<Order> orderOptional = orderRepository.getById(orderId);

        if (orderOptional.isEmpty()) {
            throw new NotFoundObjectException("Nao existe pedido em aberto");
        }
        Order order = orderOptional.get();

        if (!OrderValidators.orderIsOpen(order)) {
            throw new ClosedOrderException("Nao e possivel adcionar produtos a um pedido fechado");
        }

        ProductRepository productRepository = ProductRepository.getInstance();
        List<Product> productList = productRepository
                .listByCompanyId(order.getCompany().getId());

        Optional<Product> productOptional = productList
                .stream()
                .filter(p -> p.getId() == productId)
                .findFirst();

        if (productOptional.isEmpty()) {
            throw new NotFoundObjectException("O produto nao pertence a essa empresa");
        }
        Product product = productOptional.get();

        order.getProductList().add(product);
    }

    public String getOrders(int orderId, String attributeName) {
        Order order = orderRepository.getById(orderId).get();

        if (Validators.isNullOrEmpty(attributeName)) {
            throw new InvalidAttributeException("Atributo invalido");
        }

        String attribute = order.getAttribute(attributeName);

        if (attribute == null) {
            throw new InvalidAttributeException("Atributo nao existe");
        }

        return attribute;
    }

    public void closeOrder(int orderId) {
        Optional<Order> orderOptional = orderRepository.getById(orderId);

        if (orderOptional.isEmpty()) {
            throw new NotFoundObjectException("Pedido nao encontrado");
        }
        Order order = orderOptional.get();

        order.setStatus("preparando");
    }

    public void removeProductFromOrder(int orderId, String productName) {
        if (Validators.isNullOrEmpty(productName)) {
            throw new IllegalArgumentException("Produto invalido");
        }

        Order order = orderRepository.getById(orderId).get();

        if (!OrderValidators.orderIsOpen(order)) {
            throw new ClosedOrderException("Nao e possivel remover produtos de um pedido fechado");
        }

        List<Product> productList = order.getProductList();
        Optional<Product> productOptional = productList.stream()
                .filter(p -> p.getName().equals(productName))
                .findFirst();

        if (productOptional.isEmpty()) {
            throw new NotFoundObjectException("Produto nao encontrado");
        }
        Product product = productOptional.get();

        productList.remove(product);
    }

    public int getOrderId(int userId, int companyId, int index) {
        List<Order> orderList = orderRepository.listByCompanyId(companyId);

        return orderList.stream()
                .filter(o -> o.getCustomer().getId() == userId)
                .toList()
                .get(index)
                .getId();
    }
}

package br.ufal.ic.p2.myfood.repositories;

import br.ufal.ic.p2.myfood.models.Product;

import java.util.List;
import java.util.Optional;

public class ProductRepository extends Repository<Product> {
    private static ProductRepository instance;
    private ProductRepository() {
        super(Product.class);
    }

    // Method to access the singleton instance
    public static ProductRepository getInstance() {
        if (instance == null) {
            synchronized (ProductRepository.class) {
                if (instance == null) {
                    instance = new ProductRepository();
                }
            }
        }
        return instance;
    }

    public List<Product> listByCompanyId(int id) {
        return list().stream().filter(p -> p.getCompany().getId() == id).toList();
    }

    public Optional<Product> getById(int id) {
        return list().stream().filter(p -> p.getId() == id).findFirst();
    }
}

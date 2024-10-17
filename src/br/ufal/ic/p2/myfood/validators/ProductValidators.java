package br.ufal.ic.p2.myfood.validators;

import br.ufal.ic.p2.myfood.exceptions.product.SameNameProductException;
import br.ufal.ic.p2.myfood.models.Product;
import br.ufal.ic.p2.myfood.repositories.ProductRepository;

import java.util.Optional;

public class ProductValidators {
    public static void validateFields(String name, float price, String category) {
        if (CommonValidators.isNullOrEmpty(name)) {
            throw new IllegalArgumentException("Nome invalido");
        }

        if (CommonValidators.isNullOrEmpty(category)) {
            throw new IllegalArgumentException("Categoria invalido");
        }

        if (price < 0) {
            throw new IllegalArgumentException("Valor invalido");
        }
    }

    public static void sameNameProduct(Product product, int companyId) {
        ProductRepository productRepository = ProductRepository.getInstance();
        Optional<Product> sameNameProduct = productRepository.list()
                .stream()
                .filter(p -> p.getCompany().getId() == companyId)
                .filter(p -> p.getName().equals(product.getName()))
                .findFirst();

        if (sameNameProduct.isPresent()) {
            throw new SameNameProductException("Ja existe um produto com esse nome para essa empresa");
        }
    }
}

package br.ufal.ic.p2.myfood.validators;

import br.ufal.ic.p2.myfood.exceptions.product.SameNameProductException;
import br.ufal.ic.p2.myfood.models.Product;
import br.ufal.ic.p2.myfood.repositories.ProductRepository;

import java.util.Optional;

/**
 * Validator class for product-related operations.
 */
public class ProductValidators {

    /**
     * Validates the fields of a product.
     *
     * @param name     the name of the product.
     * @param price    the price of the product.
     * @param category the category of the product.
     * @throws IllegalArgumentException if any of the fields are invalid.
     */
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

    /**
     * Validates if a product has the same name as an existing product in the company.
     *
     * @param product   the product to check.
     * @param companyId the ID of the company.
     * @throws SameNameProductException if a product with the same name exists for the company.
     */
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

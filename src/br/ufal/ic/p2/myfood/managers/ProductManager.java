package br.ufal.ic.p2.myfood.managers;

import br.ufal.ic.p2.myfood.exceptions.InvalidAttributeException;
import br.ufal.ic.p2.myfood.exceptions.NotFoundObjectException;
import br.ufal.ic.p2.myfood.models.Company;
import br.ufal.ic.p2.myfood.models.Product;
import br.ufal.ic.p2.myfood.repositories.CompanyRepository;
import br.ufal.ic.p2.myfood.repositories.ProductRepository;
import br.ufal.ic.p2.myfood.validators.ProductValidators;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ProductManager extends Manager {
    ProductRepository productRepository = ProductRepository.getInstance();

    void cleanRepository() throws IOException {
        productRepository.clean();
    }

    void saveRepository() throws IOException {
        productRepository.save();
    }

    public int createProduct(int companyId, String productName, float price, String category) {
        CompanyRepository companyRepository = CompanyRepository.getInstance();
        Company company = companyRepository.getById(companyId).get();

        Product product = Product.create(productName, price, category, company);

        ProductValidators.sameNameProduct(product, companyId);

        productRepository.add(product);

        return product.getId();
    }

    public void updateProduct(int productId, String productName, float price, String category) {
        ProductValidators.validateFields(productName, price, category);

        Optional<Product> optionalProduct = productRepository.list()
                .stream()
                .filter(p -> p.getId() == productId)
                .findFirst();

        if (optionalProduct.isEmpty()) {
            throw new NotFoundObjectException("Produto nao cadastrado");
        }

        Product product = optionalProduct.get();

        product.setName(productName);
        product.setPrice(price);
        product.setCategory(category);
    }

    public String getProduct(String productName, int companyId, String attributeName) {
        Optional<Product> productOptional = productRepository.listByCompanyId(companyId)
                .stream()
                .filter(p -> p.getName().equals(productName))
                .findFirst();

        if (productOptional.isEmpty()) {
            throw new NotFoundObjectException("Produto nao encontrado");
        }

        Product product = productOptional.get();

        String productAttribute = product.getAttribute(attributeName);

        if (productAttribute == null) {
            throw new InvalidAttributeException("Atributo nao existe");
        }

        return productAttribute;
    }

    public String listProducts(int companyId) {
        CompanyRepository companyRepository = CompanyRepository.getInstance();
        Optional<Company> company = companyRepository.getById(companyId);

        if (company.isEmpty()) {
            throw new NotFoundObjectException("Empresa nao encontrada");
        }

        List<Product> products = productRepository.listByCompanyId(companyId);

        // TODO: Melhorar o jeito que retorna essa String
        return "{" + products.toString() + "}";
    }
}
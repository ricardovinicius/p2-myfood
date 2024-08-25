package br.ufal.ic.p2.myfood.models;

import br.ufal.ic.p2.myfood.repositories.CompanyRepository;
import br.ufal.ic.p2.myfood.types.Persistent;
import br.ufal.ic.p2.myfood.utils.Validator;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Locale;

public class Product extends Persistent {
    private static int idCounter = 1;
    private String name;
    private float price;
    private String category;
    private int companyId;

    public Product() {}

    private Product(String name, float price, String category, int companyId) {
        this.id = idCounter++;
        this.name = name;
        this.price = price;
        this.category = category;
        this.companyId = companyId;
    }

    public static Product create(String name, float price, String category, int companyId) {
        if (Validator.isNullOrEmpty(name)) {
            throw new IllegalArgumentException("Nome invalido");
        }

        if (Validator.isNullOrEmpty(category)) {
            throw new IllegalArgumentException("Categoria invalido");
        }

        if (price < 0) {
            throw new IllegalArgumentException("Valor invalido");
        }

        return new Product(name, price, category, companyId);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCompanyId() {
        return companyId;
    }

    @JsonIgnore()
    public Company getCompany() {
        CompanyRepository companyRepository = CompanyRepository.getInstance();

        return companyRepository.list()
                .stream()
                .filter(c -> c.getId() == companyId)
                .findFirst()
                .orElse(null);
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getAttribute(String attribute) {
        if (attribute.equals("nome")) {
            return name;
        }

        if (attribute.equals("categoria")) {
            return category;
        }

        if (attribute.equals("empresa")) {
            return getCompany().getName();
        }

        if (attribute.equals("valor")) {
            return String.format(Locale.US, "%.2f", price);
        }

        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}

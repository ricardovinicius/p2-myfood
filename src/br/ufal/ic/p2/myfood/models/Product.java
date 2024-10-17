package br.ufal.ic.p2.myfood.models;

import br.ufal.ic.p2.myfood.types.Persistent;
import br.ufal.ic.p2.myfood.validators.ProductValidators;

import java.util.Locale;

public class Product extends Persistent {
    private static int idCounter = 1;
    private String name;
    private float price;
    private String category;
    private Company company;

    public Product() {}

    private Product(String name, float price, String category, Company company) {
        this.id = idCounter++;
        this.name = name;
        this.price = price;
        this.category = category;
        this.company = company;
    }

    public static Product create(String name, float price, String category, Company company) {
        ProductValidators.validateFields(name, price, category);

        return new Product(name, price, category, company);
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
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

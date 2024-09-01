package br.ufal.ic.p2.myfood.models;

import br.ufal.ic.p2.myfood.types.Persistent;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Order extends Persistent {
    private static int idCounter = 1;
    private User customer;
    private Company company;
    private String status;
    private List<Product> productList;

    public Order() {}

    private Order(User customer, Company company, String status, List<Product> productList) {
        this.id = idCounter++;
        this.customer = customer;
        this.company = company;
        this.status = status;
        this.productList = productList;
    }

    public static Order create(User customer, Company company) {
        return new Order(customer, company, "aberto", new ArrayList<>());
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProducts(List<Product> productList) {
        this.productList = productList;
    }

    @JsonIgnore()
    public float getTotalPrice() {
        return productList.stream()
                .map(Product::getPrice).reduce(Float::sum)
                .orElse(0F);
    }

    public String getAttribute(String attribute) {
        if (attribute.equals("cliente")) {
            return getCustomer().getName();
        }

        if (attribute.equals("empresa")) {
            return getCompany().getName();
        }

        if (attribute.equals("estado")) {
            return status;
        }

        if (attribute.equals("produtos")) {
            return "{" + productList.toString() + "}";
        }

        if (attribute.equals("valor")) {
            return String.format(Locale.US, "%.2f", getTotalPrice());
        }

        return null;
    }

}

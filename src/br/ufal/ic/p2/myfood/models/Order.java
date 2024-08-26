package br.ufal.ic.p2.myfood.models;

import br.ufal.ic.p2.myfood.repositories.CompanyRepository;
import br.ufal.ic.p2.myfood.repositories.UserRepository;
import br.ufal.ic.p2.myfood.types.Persistent;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Order extends Persistent {
    private static int idCounter = 1;
    private int customerId;
    private int companyId;
    private String status;
    private List<Product> productList;

    public Order() {}

    private Order(int customerId, int companyId, String status, List<Product> productList) {
        this.id = idCounter++;
        this.customerId = customerId;
        this.companyId = companyId;
        this.status = status;
        this.productList = productList;
    }

    public static Order create(int customerId, int companyId) {
        return new Order(customerId, companyId, "aberto", new ArrayList<>());
    }

    @JsonIgnore()
    public User getCustomer() {
        UserRepository userRepository = UserRepository.getInstance();
        return userRepository.getById(customerId).orElse(null);
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @JsonIgnore()
    public Company getCompany() {
        CompanyRepository companyRepository = CompanyRepository.getInstance();
        return companyRepository.getById(companyId).orElse(null);
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
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

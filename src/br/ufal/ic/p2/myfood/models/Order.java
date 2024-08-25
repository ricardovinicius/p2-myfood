package br.ufal.ic.p2.myfood.models;

import br.ufal.ic.p2.myfood.types.Persistent;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

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

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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

}

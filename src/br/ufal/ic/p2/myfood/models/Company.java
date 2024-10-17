package br.ufal.ic.p2.myfood.models;

import br.ufal.ic.p2.myfood.types.Persistent;
import br.ufal.ic.p2.myfood.validators.CommonValidators;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.ArrayList;
import java.util.List;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Restaurant.class, name = "restaurant"),
        @JsonSubTypes.Type(value = Market.class, name = "market"),
        @JsonSubTypes.Type(value = DrugStore.class, name = "drugstore")
})
public abstract class Company extends Persistent {
    private static int id_counter = 1;
    private String name;
    private String address;
    private User owner;
    private List<User> deliveryList = new ArrayList<>();
    private String dtype;

    public Company() {}

    Company(String name, String address, User owner, String dtype) {
        this.id = id_counter++;
        this.name = name;
        this.address = address;
        this.owner = owner;
        this.dtype = dtype;
    }

    public static void create(String name, String address, User owner) {
        if (CommonValidators.isNullOrEmpty(name)) {
            throw new IllegalArgumentException("Nome invalido");
        }

        if (CommonValidators.isNullOrEmpty(address)) {
            throw new IllegalArgumentException("Endereco da empresa invalido");
        }
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "[" + name + ", " + address + "]";
    }

    public String getAttribute(String attribute) {
        if (attribute.equals("nome")) {
            return name;
        }

        if (attribute.equals("endereco")) {
            return address;
        }

        if (attribute.equals("dono")) {
            return getOwner().getName();
        }

        return null;
    }

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
    }

    public List<User> getDeliveryList() {
        return deliveryList;
    }

    public void setDeliveryList(List<User> deliveryList) {
        this.deliveryList = deliveryList;
    }
}

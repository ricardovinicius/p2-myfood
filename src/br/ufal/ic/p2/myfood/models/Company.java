package br.ufal.ic.p2.myfood.models;

import br.ufal.ic.p2.myfood.types.Persistent;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Restaurant.class, name = "restaurant")
})
public abstract class Company extends Persistent {
    private static int id_counter = 1;
    private String name;
    private String address;
    private User owner;

    public Company() {}

    Company(String name, String address, User owner) {
        this.id = id_counter++;
        this.name = name;
        this.address = address;
        this.owner = owner;
    }

    public static void create(String name, String address, User owner) {}

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
}

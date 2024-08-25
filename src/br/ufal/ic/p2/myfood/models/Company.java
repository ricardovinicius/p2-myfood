package br.ufal.ic.p2.myfood.models;

import br.ufal.ic.p2.myfood.repositories.Repository;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Company extends Persistent {
    private static int id_counter = 1;
    private String name;
    private String address;
    private int ownerId;

    public Company() {}

    Company(String name, String address, int ownerId) {
        this.id = id_counter++;
        this.name = name;
        this.address = address;
        this.ownerId = ownerId;
    }

    public static void create(String name, String address, int owner_id) {}

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @JsonIgnore()
    public User getOwner() {
        Repository<User> userRepository = Repository.getInstance(User.class);
        return userRepository.list()
                .stream()
                .filter(u -> u.getId() == ownerId)
                .findFirst()
                .orElse(null);
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
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

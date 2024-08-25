package br.ufal.ic.p2.myfood.models;

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
    private Unique<String> name;
    private String address;
    private int owner_id;

    public Company() {}

    Company(String name, String address, int owner_id) {
        this.id = id_counter++;
        this.name = new Unique(name);
        this.address = address;
        this.owner_id = owner_id;
    }

    public static void create(String name, String address, int owner_id) {
        return;
    }

    public String getName() {
        return name.getValue();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }
}

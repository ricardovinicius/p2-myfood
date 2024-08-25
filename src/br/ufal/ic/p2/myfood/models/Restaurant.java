package br.ufal.ic.p2.myfood.models;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("restaurant")
public class Restaurant extends Company {
    private String kitchenType;

    Restaurant() {}

    Restaurant(String name, String address, String kitchenType, int owner_id) {
        super(name, address, owner_id);
        this.kitchenType = kitchenType;
    }

    public static Restaurant create(String name, String address, String kitchenType, int owner_id) {
        Company.create(name, address, owner_id);

        return new Restaurant(name, address, kitchenType, owner_id);
    }

    public String getKitchenType() {
        return kitchenType;
    }

    public void setKitchenType(String kitchenType) {
        this.kitchenType = kitchenType;
    }

    public String getAttribute(String attribute) {
        String value = super.getAttribute(attribute);

        if (value != null) {
            return value;
        }

        if (attribute.equals("tipoCozinha")) {
            return kitchenType;
        }

        return null;
    }
}

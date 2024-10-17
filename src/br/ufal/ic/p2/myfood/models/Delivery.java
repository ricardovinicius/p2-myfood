package br.ufal.ic.p2.myfood.models;

import br.ufal.ic.p2.myfood.types.Unique;
import br.ufal.ic.p2.myfood.validators.CommonValidators;
import br.ufal.ic.p2.myfood.validators.UserValidators;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("delivery")
public class Delivery extends User {
    private String address;
    private String vehicle;
    private String licensePlate;

    Delivery() {}

    Delivery(String name, String email, String password, String address, String vehicle, String licensePlate) {
        super(name, email, password, "delivery");
        this.address = address;
        this.vehicle = vehicle;
        this.licensePlate = licensePlate;
    }

    public static Delivery create(String name, String email, String password, String address, String vehicle, String licensePlate) {
        User.create(name, email, password);

        if (CommonValidators.isNullOrEmpty(address)) {
            throw new IllegalArgumentException("Endereco invalido");
        }

        if (CommonValidators.isNullOrEmpty(vehicle)) {
            throw new IllegalArgumentException("Veiculo invalido");
        }

        if (CommonValidators.isNullOrEmpty(licensePlate)) {
            throw new IllegalArgumentException("Placa invalido");
        }

        if (UserValidators.alreadyRegistredLicensePlate(licensePlate)) {
            throw new IllegalArgumentException("Placa invalido");
        }

        return new Delivery(name, email, password, address, vehicle, licensePlate);
    }

    public String getAttribute(String attribute) {
        String value = super.getAttribute(attribute);

        if (value != null) {
            return value;
        }

        if (attribute.equals("endereco")) {
            return getAddress();
        }

        if (attribute.equals("veiculo")) {
            return getVehicle();
        }

        if (attribute.equals("placa")) {
            return getLicensePlate();
        }

        return null;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
}

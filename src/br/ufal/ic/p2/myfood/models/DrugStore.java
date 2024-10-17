package br.ufal.ic.p2.myfood.models;

import br.ufal.ic.p2.myfood.validators.MarketValidators;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("drugstore")
public class DrugStore extends Company {
    private boolean open24hours;
    private int employees;

    DrugStore() {}

    private DrugStore(String name, String address, User owner, boolean open24hours, int employees) {
        super(name, address, owner, "drugstore");
        this.open24hours = open24hours;
        this.employees = employees;
    }

    public static DrugStore create(String name, String address, User owner, boolean open24hours, int employees) {
        Company.create(name, address, owner);

        return new DrugStore(name, address, owner, open24hours, employees);
    }

    public boolean isOpen24hours() {
        return open24hours;
    }

    public void setOpen24hours(boolean open24hours) {
        this.open24hours = open24hours;
    }

    public int getEmployees() {
        return employees;
    }

    public void setEmployees(int employees) {
        this.employees = employees;
    }

    public String getAttribute(String attribute) {
        String value = super.getAttribute(attribute);

        if (value != null) {
            return value;
        }

        if (attribute.equals("aberto24Horas")) {
            return String.valueOf(open24hours);
        }

        if (attribute.equals("numeroFuncionarios")) {
            return String.valueOf(employees);
        }

        return null;
    }
}

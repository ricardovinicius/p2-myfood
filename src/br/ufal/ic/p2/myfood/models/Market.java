package br.ufal.ic.p2.myfood.models;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("market")
public class Market extends Company {
    private String openingHour;
    private String closingHour;
    private String marketType;

    Market() {}

    private Market(String name, String address, User owner, String openingHour, String closingHour, String marketType) {
        super(name, address, owner);
        this.openingHour = openingHour;
        this.closingHour = closingHour;
        this.marketType = marketType;
    }

    public static Market create(String name, String address, User owner, String openingHour, String closingHour, String marketType) {
        return new Market(name, address, owner, openingHour, closingHour, marketType);
    }

    public String getOpeningHour() {
        return openingHour;
    }

    public void setOpeningHour(String openingHour) {
        this.openingHour = openingHour;
    }

    public String getClosingHour() {
        return closingHour;
    }

    public void setClosingHour(String closingHour) {
        this.closingHour = closingHour;
    }

    public String getMarketType() {
        return marketType;
    }

    public void setMarketType(String marketType) {
        this.marketType = marketType;
    }

    public String getAttribute(String attribute) {
        String value = super.getAttribute(attribute);

        if (value != null) {
            return value;
        }

        return null;
    }
}

package pl.sda;

import java.util.List;

import static java.util.Arrays.asList;

public class Address {

    private String city;
    private String street;
    private int buildingNumber;
    private int doorNumber;
    private String postalCode;

    public Address(String city, String street, int buildingNumber, int doorNumber, String postalCode) {
        this.city = city;
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.doorNumber = doorNumber;
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public int getBuildingNumber() {
        return buildingNumber;
    }

    public int getDoorNumber() {
        return doorNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    @Override
    public String toString() {
        return "ulica " + street +
                " " + buildingNumber +
                "/" + doorNumber +
                ", " + city +
                "\nkod pocztowy: " + postalCode;
    }

}

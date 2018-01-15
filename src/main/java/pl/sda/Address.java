package pl.sda;

public class Address {

    private String city;
    private int buildingNumber;
    private int doorNumber;
    private String postalCode;

    public Address(String city, int buildingNumber, int doorNumber, String postalCode) {
        this.city = city;
        this.buildingNumber = buildingNumber;
        this.doorNumber = doorNumber;
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
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
}

package pl.sda;

public enum PaymentMethod {
    POSTAL_TRANSFER("przelew pocztowy"),
    BANK_TRANSFER("przelew bankowy"),
    CASH("odbiór w kasie");

    private String description;

    PaymentMethod(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

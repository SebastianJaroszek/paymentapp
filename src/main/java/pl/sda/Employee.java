package pl.sda;

public abstract class Employee {
    private String name;
    private String surname;
    private Address address;
    private String bankAccountNumber;
    private String pesel;
    private PaymentMethod paymentMethod;

    public Employee(String name, String surname, Address address, String bankAccountNumber, String pesel, PaymentMethod paymentMethod) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.bankAccountNumber = bankAccountNumber;
        this.pesel = pesel;
        this.paymentMethod = paymentMethod;
    }

    public PaymentMethod getPaymentMethod() {
        return this.paymentMethod;
    }
}

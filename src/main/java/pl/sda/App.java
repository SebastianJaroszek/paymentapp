package pl.sda;

import com.google.gson.Gson;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) {
        String json = "{\"twoWeekSalary\":100,\"commision\":0.25,\"bills\":[],\"name\":\"Adam\",\"surname\":\"Małysz\",\"address\":{\"city\":\"Lublin\",\"street\":\"Al. Racławickie\",\"buildingNumber\":35,\"doorNumber\":2,\"postalCode\":\"20-012\"},\"bankAccountNumber\":\"04 2004 0000 3043 2349 4930 1111\",\"pesel\":\"98031450223\",\"paymentMethod\":\"BANK_TRANSFER\"}";
        Gson gson = new Gson();
        CommisionalEmployee commisionalEmployee = gson.fromJson(json, CommisionalEmployee.class);
        System.out.println(commisionalEmployee);

        List<Payable> employees = createEmployees();
        LocalDate day = readDate();
        showPayments(employees, day);
    }

    private static List<Employee> castToEmployee(List<Payable> payables){
        return payables.stream()
                .map(payable -> (Employee)payable)
                .collect(Collectors.toList());
    }

    private static void showPayments(List<Payable> payables, LocalDate day) {
        System.out.println("Wypłaty na dzień: " + day);

        List<Employee> employees = castToEmployee(payables);

        for (int i = 0; i < employees.size(); i++){
            System.out.println(employees.get(i) + "\nwypłata " + payables.get(i).calculatePayment(day) + " PLN");
        }
    }

    private static LocalDate readDate() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj dzień obliczania wypłat w formaacie YYYY-MM-DD" +
                " lub naciśnij ENTER, aby przyjąć dzisiejszy dzień.");
        String line = scanner.nextLine();
        LocalDate day;
        try {
            day = LocalDate.parse(line);
        } catch (DateTimeParseException ex) {
            day = LocalDate.now();
        }
        return day;
    }

    private static List<Payable> createEmployees() {
        List<Payable> employees = new ArrayList<>();
        addHourlyEmployee(employees);
        addMonthlyEmpoyee(employees);
        addCommisionalEmployee(employees);
        return employees;
    }

    private static void addCommisionalEmployee(List<Payable> employees) {
        CommisionalEmployee commisionalEmployee = new CommisionalEmployee(new BigDecimal(100),
                new BigDecimal("0.25"));
        commisionalEmployee.setName("Adam");
        commisionalEmployee.setSurname("Małysz");
        Address address = new Address("Lublin", "Al. Racławickie", 35, 2, "20-012");
        commisionalEmployee.setAddress(address);
        commisionalEmployee.setBankAccountNumber("04 2004 0000 3043 2349 4930 1111");
        commisionalEmployee.setPesel("98031450223");
        commisionalEmployee.setPaymentMethod(PaymentMethod.BANK_TRANSFER);

        Gson gson = new Gson();
        String jsonResult = gson.toJson(commisionalEmployee);
        System.out.println(jsonResult);


        commisionalEmployee.addBill(new Bill(LocalDate.of(2018, 1, 10), new BigDecimal(150)));
        commisionalEmployee.addBill(new Bill(LocalDate.of(2018, 1, 11), new BigDecimal(200)));
        employees.add(commisionalEmployee);
    }

    private static void addMonthlyEmpoyee(List<Payable> employees) {
        MonthlyEmployee monthlyEmployee = new MonthlyEmployee(new BigDecimal(1000));
        monthlyEmployee.setName("Mariusz");
        monthlyEmployee.setSurname("Pudzianowski");
        Address address = new Address("Warszawa", "Słoneczna", 53, 4, "50-055");
        monthlyEmployee.setAddress(address);
        monthlyEmployee.setBankAccountNumber("04 3333 5555 6666 7777 8888 9999");
        monthlyEmployee.setPesel("75051961032");
        monthlyEmployee.setPaymentMethod(PaymentMethod.POSTAL_TRANSFER);
        employees.add(monthlyEmployee);
    }

    private static void addHourlyEmployee(List<Payable> employees) {
        HourlyEmployee hourlyEmployee = new HourlyEmployee(new BigDecimal(44));
        hourlyEmployee.setName("Jan");
        hourlyEmployee.setSurname("Kowalski");
        Address address = new Address("Wrocław", "Szkolna", 60, 18, "70-555");
        hourlyEmployee.setAddress(address);
        hourlyEmployee.setBankAccountNumber("04 9999 8888 7777 6666 5555 4444");
        hourlyEmployee.setPesel("63021035353");
        hourlyEmployee.setPaymentMethod(PaymentMethod.CASH);
        hourlyEmployee.addWorkingDay(new WorkingDay(LocalDate.of(2018, 1, 3), 7));
        hourlyEmployee.addWorkingDay(new WorkingDay(LocalDate.of(2018, 1, 4), 5));
        hourlyEmployee.addWorkingDay(new WorkingDay(LocalDate.of(2018, 1, 11), 5));
        hourlyEmployee.addWorkingDay(new WorkingDay(LocalDate.of(2018, 1, 7), 5));
        employees.add(hourlyEmployee);
    }

}

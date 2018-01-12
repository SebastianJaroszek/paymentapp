package pl.sda;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        List<Payable> employees = createEmployees();
        LocalDate day = readDate();
        showPayments(employees, day);
    }

    private static void showPayments(List<Payable> payables, LocalDate day) {
        System.out.println("Wypłaty na dzień: " + day);
        /*for (int i = 0; i < payables.size(); i++) {
            System.out.println((i + 1) + ". " + payables.get(i).calculatePayment(day));
        }*/
        payables.stream()
                .forEach(payable -> System.out.println(payable.calculatePayment(day)));
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
        commisionalEmployee.addBill(new Bill(LocalDate.of(2018, 1, 10), new BigDecimal(150)));
        commisionalEmployee.addBill(new Bill(LocalDate.of(2018, 1, 11), new BigDecimal(200)));
        employees.add(commisionalEmployee);
    }

    private static void addMonthlyEmpoyee(List<Payable> employees) {
        MonthlyEmployee monthlyEmployee = new MonthlyEmployee(new BigDecimal(1000));
        employees.add(monthlyEmployee);
    }

    private static void addHourlyEmployee(List<Payable> employees) {
        HourlyEmployee hourlyEmployee = new HourlyEmployee(new BigDecimal(44));
        hourlyEmployee.addWorkingDay(new WorkingDay(LocalDate.of(2018, 1, 3), 7));
        hourlyEmployee.addWorkingDay(new WorkingDay(LocalDate.of(2018, 1, 4), 5));
        hourlyEmployee.addWorkingDay(new WorkingDay(LocalDate.of(2018, 1, 11), 5));
        employees.add(hourlyEmployee);
    }

}

package pl.sda;

import com.sun.xml.internal.bind.v2.TODO;
import lombok.Data;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static pl.sda.DateUtils.findMonday;
import static pl.sda.DateUtils.findSaturday;
import static pl.sda.DateUtils.isWorkingDay;

public class HourlyEmployee extends Employee implements Payable {

    private static final int WORKING_HOURS = 8;
    private static final BigDecimal OVERHOURS_RATE = new BigDecimal("1.5");
    private static final BigDecimal WEEKEND_OVERHOURS_RATE = new BigDecimal("2");

    private final BigDecimal hourlyRate;
    private final List<WorkingDay> workingDays;

    /*public HourlyEmployee(String name, String surname, Address address, String bankAccountNumber, String pesel,
                          PaymentMethod paymentMethod, BigDecimal hourlyRate, List<WorkingDay> workingDays) {
        super(name, surname, address, bankAccountNumber, pesel, paymentMethod);
        this.hourlyRate = hourlyRate;
        this.workingDays = workingDays;
    }*/

    public HourlyEmployee(BigDecimal hourlyRate) {
        this.hourlyRate = hourlyRate;
        this.workingDays = new ArrayList<>();
    }

    public void addWorkingDay(WorkingDay workingDay) {
        workingDays.add(workingDay);
    }

    @Override
    public boolean isPaymentDay(LocalDate day) {
        return day.getDayOfWeek() == DayOfWeek.FRIDAY;
    }

    @Override
    public BigDecimal calculatePayment(LocalDate day) {
        if (isPaymentDay(day)) {
            LocalDate saturday = findSaturday(day);
            List<WorkingDay> weekWorkingDays = findWorkingDays(saturday, day);
            return calculatePayment(weekWorkingDays);
        } else {
            return BigDecimal.ZERO;
        }
    }

    private List<WorkingDay> findWorkingDays(LocalDate fromDay, LocalDate toDay) {
        return workingDays.stream()
                .filter(workingDay -> workingDay.isBetween(fromDay, toDay))
                .collect(Collectors.toList());
    }

    private BigDecimal calculatePayment(List<WorkingDay> weekWorkingDays) {
        return weekWorkingDays.stream()
                .map(workingDay -> calculatePayment(workingDay))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculatePayment(WorkingDay workingDay) {
        int workingHours = workingDay.getHours();
        if (!isWorkingDay(workingDay.getDate())) {
            BigDecimal payment = hourlyRate.multiply(new BigDecimal(workingHours));
            return payment.multiply(WEEKEND_OVERHOURS_RATE);
        } else if (workingHours <= WORKING_HOURS) {
            return hourlyRate.multiply(new BigDecimal(workingHours));
        } else {
            int overHours = workingHours - WORKING_HOURS;
            int normalHours = workingHours - overHours;
            BigDecimal payment = BigDecimal.ZERO;
            payment = payment.add(new BigDecimal(normalHours).multiply(hourlyRate));
            payment = payment.add(new BigDecimal(overHours).multiply(hourlyRate.multiply(OVERHOURS_RATE)));
            return payment;
        }
    }
}

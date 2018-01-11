package pl.sda;

import lombok.Data;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class HourlyEmployee implements Payable {

    private static final int WORKING_HOURS = 8;
    private static final BigDecimal OVERHOURS_RATE = new BigDecimal("1.5");

    private final BigDecimal hourlyRate;
    private final List<WorkingDay> workingDays;

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
            LocalDate monday = findMonday(day);
            List<WorkingDay> weekWorkingDays = findWorkingDays(monday, day);
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

    /**
     * Znajduje poniedziałek dla danego piątku
     *
     * @param day piątek
     * @return pasujący poniedziałek
     */
    private LocalDate findMonday(LocalDate day) {
        if (day.getDayOfWeek() != DayOfWeek.FRIDAY) {
            throw new IllegalStateException("Metoda findMonday() musi być wywołana dla piątku.");
        }
        return day.minusDays(4);
    }

    private BigDecimal calculatePayment(List<WorkingDay> weekWorkingDays) {
        BigDecimal payment = BigDecimal.ZERO;
        for (WorkingDay workingDay : weekWorkingDays) {
            payment = payment.add(calculatePayment(workingDay));
        }
        return payment;
    }

    public BigDecimal calculatePayment(WorkingDay workingDay) {
        int workingHours = workingDay.getHours();
        if (workingHours <= WORKING_HOURS){
            return hourlyRate.multiply(new BigDecimal(workingHours));
        } else {
            int overHours = workingHours - WORKING_HOURS;
            int normalHours = workingHours - overHours;
            BigDecimal payment = BigDecimal.ZERO;
            payment = payment.add(new BigDecimal(normalHours).multiply(hourlyRate));
            payment = payment.add(new BigDecimal(overHours).multiply(hourlyRate.multiply(OVERHOURS_RATE)));
            return payment;

            /*BigDecimal normalPayment = hourlyRate
                    .multiply(new BigDecimal(WORKING_HOURS));
            int overhours = workingDay.getHours() - WORKING_HOURS;
            BigDecimal overhourPayment = hourlyRate
                    .multiply(OVERHOURS_RATE)
                    .multiply(new BigDecimal(overhours));
            return normalPayment
                    .add(overhourPayment);*/

        }
    }
}

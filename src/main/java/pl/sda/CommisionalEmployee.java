package pl.sda;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CommisionalEmployee implements Payable {

    private BigDecimal twoWeekSalary;
    private BigDecimal commision;
    private List<Bill> bills;

    private static final Set<DayOfWeek> FREE_DAYS = new HashSet<>();

    static {
        FREE_DAYS.add(DayOfWeek.SATURDAY);
        FREE_DAYS.add(DayOfWeek.SUNDAY);
    }

    public CommisionalEmployee(BigDecimal twoWeekSalary, BigDecimal commision) {
        this.twoWeekSalary = twoWeekSalary;
        this.commision = commision;
        this.bills = new ArrayList<>();
    }

    public void addBill(Bill bill) {
        bills.add(bill);
    }

    @Override
    public boolean isPaymentDay(LocalDate day) {
        return isSecondFriday(day) || isLastWorkingDayOfYear(day);
    }

    private boolean isLastWorkingDayOfYear(LocalDate day) {
        LocalDate lastDay = LocalDate.of(day.getYear(), 12, day.lengthOfMonth());
        while (FREE_DAYS.contains(lastDay.getDayOfWeek())) {
            lastDay = lastDay.minusDays(1);
        }
        return day.equals(lastDay);
    }

    private boolean isSecondFriday(LocalDate day) {
        LocalDate secondFriday = findSecondFriday(day.getYear());
        int days = day.getDayOfYear() - secondFriday.getDayOfYear();
        return days % 14 == 0;
    }

    private LocalDate findSecondFriday(int year) {
        LocalDate date = LocalDate.of(year, 1, 1);
        while (date.getDayOfWeek() != DayOfWeek.FRIDAY) {
            date = date.plusDays(1);
        }
        return date.plusDays(7);
    }


    /*
    przypadki:
    od 1 stycznia do drugiego piątku w roku
    od co drugiego piątku do poniedziałku dwa tygodnie wcześniej
    od końca roku do poniedziałku przypadającego po ostatnim co drugim piątku
     */
    @Override
    public BigDecimal calculatePayment(LocalDate day) {
        if (isPaymentDay(day)) {
            LocalDate from = findMatchingFirstDay(day);
            int workingDays = countWorkingDays(from, day);
            List<Bill> bills = findBills(from, day);
            return calculatePayment(bills, workingDays);
        } else {
            return BigDecimal.ZERO;
        }
    }

    private int countWorkingDays(LocalDate fromDate, LocalDate toDate) {
        int workingDays = 0;
        do {
            if (!(FREE_DAYS.contains(fromDate.getDayOfWeek()))) {
                workingDays++;
            }
            fromDate = fromDate.plusDays(1);
        }
        while (fromDate.compareTo(toDate) <= 0);
        return workingDays;
    }

    private BigDecimal calculatePayment(List<Bill> bills, int workingDays) {
        BigDecimal sum = BigDecimal.ZERO;
        for (Bill bill : bills) {
            sum = sum.add(bill.getValue());
        }
        BigDecimal oneDaySalary = twoWeekSalary.divide(new BigDecimal(10));
        return oneDaySalary
                .multiply(new BigDecimal(workingDays))
                .add(sum.multiply(commision));
    }

    /**
     * Metoda znajduje pierwszy dzień okresu do wypłaty
     *
     * @param day ostatni dzień okresu
     * @return pierwszy dzień okresu
     */
    private LocalDate findMatchingFirstDay(LocalDate day) {
        if (day.equals(findSecondFriday(day.getYear()))) {
            return LocalDate.of(day.getYear(), 1, 1);
        } else if (isLastWorkingDayOfYear(day)) {
            while (!isSecondFriday(day)) {
                day = day.minusDays(1);
            }
            return day.plusDays(3);
        } else {
            return day.minusDays(11);
        }
    }

    private List<Bill> findBills(LocalDate fromDate, LocalDate toDate) {
        List<Bill> foundedBills = bills.stream()
                .filter(bill -> bill.isBetween(fromDate, toDate))
                .collect(Collectors.toList());
        return foundedBills;
    }

}

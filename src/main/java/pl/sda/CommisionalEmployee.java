package pl.sda;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static pl.sda.DateUtils.*;

public class CommisionalEmployee implements Payable {

    private BigDecimal twoWeekSalary;
    private BigDecimal commision;
    private List<Bill> bills;



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
        while (fromDate.compareTo(toDate) <= 0) {
            if (isWorkingDay(fromDate)) {
                workingDays++;
            }
            fromDate = fromDate.plusDays(1);
        }
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
        return bills.stream()
                .filter(bill -> bill.isBetween(fromDate, toDate))
                .collect(Collectors.toList());
    }

}

package pl.sda;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommisionalEmployee implements Payable {

    private BigDecimal twoWeekSalary;
    private List<Bill> bills;

    private static final Set<DayOfWeek> FREE_DAYS = new HashSet<>();

    static {
        FREE_DAYS.add(DayOfWeek.SATURDAY);
        FREE_DAYS.add(DayOfWeek.SUNDAY);
    }

    public CommisionalEmployee(BigDecimal twoWeekSalary) {
        this.twoWeekSalary = twoWeekSalary;
        this.bills = new ArrayList<>();
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

    @Override
    public BigDecimal calculatePayment(LocalDate day) {
        return null;
    }
}

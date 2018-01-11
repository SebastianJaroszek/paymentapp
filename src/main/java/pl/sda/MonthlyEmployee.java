package pl.sda;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class MonthlyEmployee implements Payable {

    private static final Set<DayOfWeek> FREE_DAYS = new HashSet<>();

    static {
        FREE_DAYS.add(DayOfWeek.SATURDAY);
        FREE_DAYS.add(DayOfWeek.SUNDAY);
    }

    private final BigDecimal salary;

    public MonthlyEmployee(BigDecimal salary) {
        this.salary = salary;
    }

    @Override
    public boolean isPaymentDay(LocalDate day) {
        LocalDate lastDay = LocalDate.of(day.getYear(), day.getMonth(), day.lengthOfMonth());
        while (FREE_DAYS.contains(lastDay.getDayOfWeek())) {
            lastDay = lastDay.minusDays(1);
        }
        return day.equals(lastDay);
        //lengthOfMonth
        /*if ((day.getDayOfWeek() != DayOfWeek.SATURDAY && day.getDayOfWeek() != DayOfWeek.SUNDAY)
                && (day.getDayOfMonth() == day.lengthOfMonth())) {*/
       /* if (!FREE_DAYS.contains(day.getDayOfWeek()) && day.getDayOfMonth() == day.lengthOfMonth()){
            return true;
        }
        return false;
        */
    }

    @Override
    public BigDecimal calculatePayment(LocalDate day) {
        if (isPaymentDay(day)) {
            return salary;
        } else {
            return BigDecimal.ZERO;
        }
    }
}

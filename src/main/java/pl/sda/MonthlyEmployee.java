package pl.sda;

import java.math.BigDecimal;
import java.time.LocalDate;

import static pl.sda.DateUtils.isWorkingDay;

public class MonthlyEmployee implements Payable {

    private final BigDecimal salary;

    public MonthlyEmployee(BigDecimal salary) {
        this.salary = salary;
    }

    @Override
    public boolean isPaymentDay(LocalDate day) {
        LocalDate lastDay = LocalDate.of(day.getYear(), day.getMonth(), day.lengthOfMonth());
        while (!isWorkingDay(lastDay)) {
            lastDay = lastDay.minusDays(1);
        }
        return day.equals(lastDay);
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

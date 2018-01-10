package pl.sda;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public void addWorkingDay(WorkingDay workingDay){
        workingDays.add(workingDay);
    }

    @Override
    public boolean isPaymentDay(LocalDate day) {
        return false;
    }

    @Override
    public BigDecimal calculatePayment(LocalDate day) {
        return null;
    }
}

package pl.sda;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CommisionalEmployee implements Payable {
    @Override
    public boolean isPaymentDay(LocalDate day) {
        return false;
    }

    @Override
    public BigDecimal calculatePayment(LocalDate day) {
        return null;
    }
}

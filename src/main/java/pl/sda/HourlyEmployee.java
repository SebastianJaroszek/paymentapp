package pl.sda;

import java.math.BigDecimal;
import java.util.Date;

public class HourlyEmployee implements Payable {
    @Override
    public boolean isPaymentDay(Date day) {
        return false;
    }

    @Override
    public BigDecimal calculatePayment(Date day) {
        return null;
    }
}

package pl.sda;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface Payable {

    boolean isPaymentDay(LocalDate day);

    BigDecimal calculatePayment(LocalDate day);

}

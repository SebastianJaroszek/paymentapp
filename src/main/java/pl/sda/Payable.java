package pl.sda;

import java.math.BigDecimal;
import java.util.Date;

public interface Payable {

    boolean isPaymentDay(Date day);

    BigDecimal calculatePayment(Date day);

}

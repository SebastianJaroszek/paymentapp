package pl.sda;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommisionalEmployeeTest {

    private static final LocalDate SECOND_FRIDAY = LocalDate.of(2018, 1, 12);
    private static final BigDecimal TWO_WEEK_SALARY = new BigDecimal(100);
    private static final BigDecimal COMMISION = new BigDecimal("0.25");
    private static final BigDecimal BILL_VALUE = new BigDecimal(100);
    private CommisionalEmployee commisionalEmployee;

    @BeforeEach
    void setup(){
        commisionalEmployee = new CommisionalEmployee(TWO_WEEK_SALARY, COMMISION);
    }

    @Test
    void isPaymentDayShouldReturnTrueForEverySecondFriday(){
        assertTrue(commisionalEmployee.isPaymentDay(SECOND_FRIDAY));
        assertTrue(commisionalEmployee.isPaymentDay(SECOND_FRIDAY.plusDays(14)));
        assertTrue(commisionalEmployee.isPaymentDay(SECOND_FRIDAY.plusDays(28)));
    }

    @Test
    void isPaymentDayShouldReturnFalseForNonFriday(){
        assertFalse(commisionalEmployee.isPaymentDay(SECOND_FRIDAY.minusDays(1)));
    }

    //piątek ale nie co drugi
    @Test
    void isPaymentDayShouldReturnFalseForNotEverySecondFriday(){
        assertFalse(commisionalEmployee.isPaymentDay(SECOND_FRIDAY.minusDays(7)));
        assertFalse(commisionalEmployee.isPaymentDay(SECOND_FRIDAY.plusDays(7)));
    }

    @Test
    void isPaymentDayShouldReturnTrueForLastWorkingDay(){
        assertTrue(commisionalEmployee.isPaymentDay(LocalDate.of(2018, 12, 31)));
    }

    @Test
    void isPaymentDayShouldReturnFalseForLastNonWorkingDay(){
        assertFalse(commisionalEmployee.isPaymentDay(LocalDate.of(2018, 12, 30)));
    }

    @Test
    void calculatePaymentShouldReturnCorrectPaymentAtTheBeginningOfTheYear(){
        //zakres od 1 do 12 stycznia
        //payment day 12 stycznia
        //dwa rachunki wewnątrz
        commisionalEmployee.addBill(new Bill(SECOND_FRIDAY.minusDays(3), BILL_VALUE));
        commisionalEmployee.addBill(new Bill(SECOND_FRIDAY.minusDays(6), BILL_VALUE));
        BigDecimal actualPayment = commisionalEmployee.calculatePayment(SECOND_FRIDAY);
        BigDecimal expected = TWO_WEEK_SALARY.add(BILL_VALUE.add(BILL_VALUE).multiply(COMMISION));
        assertTrue(expected.compareTo(actualPayment) == 0);
    }

    @Test
    void calculatePaymentShouldReturnCorrectPaymentAtTheEndOfTheYear(){
        //payment day 31 grudnia
        //rachunek z 31 grudnia
        LocalDate paymentDay = LocalDate.of(2018, 12, 31);
        commisionalEmployee.addBill(new Bill(paymentDay, BILL_VALUE));
        BigDecimal actualPayment = commisionalEmployee.calculatePayment(paymentDay);
        BigDecimal expected = TWO_WEEK_SALARY
                .divide(new BigDecimal(10))
                .add(BILL_VALUE.multiply(COMMISION));
        assertTrue(expected.compareTo(actualPayment) == 0);
    }

    @Test
    void calculatePaymentShouldReturnCorrectPaymentInTheMiddleOfTheYear(){
        //payment day 26 stycznia
        //dwa rachunki od 15 stycznia do 26 stycznia
        commisionalEmployee.addBill(new Bill(LocalDate.of(2018, 1, 16), BILL_VALUE));
        commisionalEmployee.addBill(new Bill(LocalDate.of(2018, 1, 25), BILL_VALUE));
        BigDecimal actualPayment = commisionalEmployee.calculatePayment(LocalDate.of(2018, 1, 26));
        BigDecimal expected = TWO_WEEK_SALARY.add(BILL_VALUE.add(BILL_VALUE).multiply(COMMISION));
        assertTrue(expected.compareTo(actualPayment) == 0);
    }
}

package pl.sda;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MonthlyEmployeeTest {

    private static final BigDecimal SALARY = new BigDecimal(200);

    private MonthlyEmployee monthlyEmployee;

    @BeforeEach
    void setup() {
        monthlyEmployee = new MonthlyEmployee(SALARY);
    }

    @Test
    void isPaymentDayShouldReturnTrueForLastWorkingDayOfMonth() {
        assertTrue(monthlyEmployee.isPaymentDay(LocalDate.of(2018, 1, 31)));
        assertTrue(monthlyEmployee.isPaymentDay(LocalDate.of(2018, 3, 30)));
        assertTrue(monthlyEmployee.isPaymentDay(LocalDate.of(2019, 3, 29)));
    }

    @Test
    void isPaymentDayShouldReturnFalseForSaturdayAndSunday() {
        assertFalse(monthlyEmployee.isPaymentDay(LocalDate.of(2018, 3, 31)));
        assertFalse(monthlyEmployee.isPaymentDay(LocalDate.of(2019, 3, 31)));
    }

    @Test
    void isPaymentDayShouldReturnFalseForNonLastWorkingDay() {
        assertFalse(monthlyEmployee.isPaymentDay(LocalDate.of(2018, 4, 27)));
    }

    @Test
    void calculatePaymentShouldReturnPaymentForLastWorkingDay() {
        assertTrue(monthlyEmployee
                .calculatePayment(LocalDate.of(2018, 1, 31))
                .compareTo(SALARY) == 0);
    }

    @Test
    void calculatePaymentShouldReturnZeroForNonLastWorkingDay() {
        assertTrue(monthlyEmployee
                .calculatePayment(LocalDate.of(2018, 3, 31))
                .compareTo(BigDecimal.ZERO) == 0);
    }
}

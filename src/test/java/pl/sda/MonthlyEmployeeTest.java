package pl.sda;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MonthlyEmployeeTest {

    @Test
    void isPaymentDayShouldReturnTrueForLastWorkingDayOfMonth(){
        MonthlyEmployee monthlyEmployee = new MonthlyEmployee();
        assertTrue(monthlyEmployee.isPaymentDay(LocalDate.of(2018, 1, 31)));
        assertTrue(monthlyEmployee.isPaymentDay(LocalDate.of(2018, 3, 30)));
        assertTrue(monthlyEmployee.isPaymentDay(LocalDate.of(2019, 3, 29)));
    }

    @Test
    void isPaymentDayShouldReturnFalseForSaturdayAndSunday() {
        MonthlyEmployee monthlyEmployee = new MonthlyEmployee();
        assertFalse(monthlyEmployee.isPaymentDay(LocalDate.of(2018, 3, 31)));
        assertFalse(monthlyEmployee.isPaymentDay(LocalDate.of(2019, 3, 31)));
    }

    @Test
    void isPaymentDayShouldReturnFalseForNonLastWorkingDay() {
        MonthlyEmployee monthlyEmployee = new MonthlyEmployee();
        assertFalse(monthlyEmployee.isPaymentDay(LocalDate.of(2018, 4, 27)));
    }
}

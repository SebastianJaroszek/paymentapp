package pl.sda;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HourlyEmployeeTest {

    //testy metody isPaymentDay(LocalDate day)
    //metoda powinna zwrócić true dla piątku
    //metoda powinna zwrócić false dla dnia innego niż piątek

    //testy metody calculatePayment(LocalDate day)
    //wywołanie metody w dzień inny niż piątek (0?)
    //wywołanie metody w piątek:
    //  nie ma zupełnie dni pracujących
    //  są dni pracujące poza rozpatrywanym tygodniem (przed i po tygodniu)
    //  są normalne godziny tylko w jeden dzień
    //  są nadgodziny ale tylko w jednym dniu
    //  są przynajmniej dwa dni przepracowane


    //metoda powinna zwrócić true dla piątku
    @Test
    void shouldReturnTrueForFriday() {
        LocalDate friday = LocalDate.of(2018, 1, 12);
        HourlyEmployee hourlyEmployee = new HourlyEmployee(new BigDecimal("10"));
        assertTrue(hourlyEmployee.isPaymentDay(friday));
    }

    //metoda powinna zwrócić false dla dnia innego niż piątek
    @Test
    void shouldReturnFalseForOtherDayThanFriday() {
        LocalDate monday = LocalDate.of(2018, 1, 8);
        HourlyEmployee hourlyEmployee = new HourlyEmployee(new BigDecimal("10"));
        assertFalse(hourlyEmployee.isPaymentDay(monday));
    }

    @Test
    void paymentShouldBeCalculated() {
        HourlyEmployee hourlyEmployee = new HourlyEmployee(new BigDecimal(10));
        LocalDate localDate = LocalDate.of(2018, 1, 12);
        WorkingDay workingDay = new WorkingDay(localDate, 12);
        BigDecimal calculatePayment = hourlyEmployee.calculatePayment(workingDay);
        assertEquals(new BigDecimal("140.0"), calculatePayment);
    }
}

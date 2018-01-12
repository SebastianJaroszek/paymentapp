package pl.sda;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HourlyEmployeeTest {

    private static final BigDecimal HOURLY_RATE = new BigDecimal("10");
    private static final LocalDate FRIDAY = LocalDate.of(2018, 1, 12);
    private static final LocalDate MONDAY = LocalDate.of(2018, 1, 8);
    private HourlyEmployee hourlyEmployee;

    @BeforeEach
    void setup() {
        hourlyEmployee = new HourlyEmployee(HOURLY_RATE);
    }

    //metoda powinna zwrócić true dla piątku
    @Test
    void shouldReturnTrueForFriday() {
        assertTrue(hourlyEmployee.isPaymentDay(FRIDAY));
    }

    //metoda powinna zwrócić false dla dnia innego niż piątek
    @Test
    void shouldReturnFalseForOtherDayThanFriday() {
        assertFalse(hourlyEmployee.isPaymentDay(MONDAY));
    }

    //wywołanie metody calculatePayment(day) w dzień inny niż piątek (0?)
    @Test
    void calculatePaymentShouldReturnZeroOnNotFriday() {
        assertEquals(BigDecimal.ZERO, hourlyEmployee.calculatePayment(MONDAY));
    }

    //wywołanie metody calculatePayment(day) w piątek:
    //  nie ma zupełnie dni pracujących
    @Test
    void calculatePaymentShouldReturnZeroOnFridayWhileWorkingDaysNotExist(){
        assertEquals(BigDecimal.ZERO, hourlyEmployee.calculatePayment(FRIDAY));
    }

    //wywołanie metody calculatePayment(day) w piątek:
    //  są dni pracujące poza rozpatrywanym tygodniem (przed i po tygodniu)
    @Test
    void calculatePaymentShouldReturnZeroWithWorkingDaysOutOfRange(){
        hourlyEmployee.addWorkingDay(new WorkingDay(FRIDAY.minusDays(10), 8));
        hourlyEmployee.addWorkingDay(new WorkingDay(FRIDAY.minusDays(10), 8));
        assertEquals(BigDecimal.ZERO, hourlyEmployee.calculatePayment(FRIDAY));
    }

    //wywołanie metody calculatePayment(day) w piątek:
    //  są normalne godziny tylko w jeden dzień
    @Test
    void calculatePaymentShouldReturnPaymentFromOneDay(){
        hourlyEmployee.addWorkingDay(new WorkingDay(MONDAY, 8));
        assertEquals(hourlyEmployee.calculatePayment(FRIDAY), HOURLY_RATE.multiply(new BigDecimal(8)));
    }

    //wywołanie metody w piątek:
    //  są nadgodziny ale tylko w jednym dniu
    @Test
    void calculatePaymentShouldReturnOverhourPaymentFromOneDay(){
        hourlyEmployee.addWorkingDay(new WorkingDay(MONDAY, 12));
        //assertEquals(hourlyEmployee.calculatePayment(FRIDAY), new BigDecimal("140.0"));
        BigDecimal payment = hourlyEmployee.calculatePayment(FRIDAY);
        assertTrue(new BigDecimal("140").compareTo(payment) == 0);
    }

    //wywołanie metody w piątek:
    //  są przynajmniej dwa dni przepracowane
    @Test
    void calculatePaymentShouldReturnPaymentFromTwoDays(){
        hourlyEmployee.addWorkingDay(new WorkingDay(FRIDAY, 8));
        hourlyEmployee.addWorkingDay(new WorkingDay(FRIDAY.minusDays(1), 10));
        BigDecimal payment = hourlyEmployee.calculatePayment(FRIDAY);
        assertTrue(new BigDecimal("190").compareTo(payment) == 0);
    }
}

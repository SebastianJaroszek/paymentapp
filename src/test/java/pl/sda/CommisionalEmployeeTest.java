package pl.sda;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommisionalEmployeeTest {

    private static final LocalDate SECOND_FRIDAY = LocalDate.of(2018, 1, 12);
    private CommisionalEmployee commisionalEmployee;

    @BeforeEach
    void setup(){
        commisionalEmployee = new CommisionalEmployee(new BigDecimal(2000));
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
}

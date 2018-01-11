package pl.sda;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WorkingDayTest {


    //private static final int WORKING_HOURS = 8;
    private static final LocalDate LOCAL_DATE = LocalDate.of(2018, 1, 11);
    private static final WorkingDay WORKING_DAY = new WorkingDay(LOCAL_DATE, 8);

    //working day jest wewnątrz 2 dat
    @Test
    @DisplayName("isBetween should return true between dates")
    void isBetweenShouldReturnTrueBetweenDates() {
        //lewa granica przedziału
        assertTrue(WORKING_DAY.isBetween(LOCAL_DATE, LOCAL_DATE.plusDays(1)));
        //wnętrze
        assertTrue(WORKING_DAY.isBetween(LOCAL_DATE.minusDays(1), LOCAL_DATE.plusDays(1)));
        //prawa granica przedziału
        assertTrue(WORKING_DAY.isBetween(LOCAL_DATE.minusDays(1),LOCAL_DATE));
    }

    @Test
    @DisplayName("isBetween should return false outside dates")
    void isBetweenShouldReturnFalseOutsideDates() {
        //poza przedziałem
        assertFalse(WORKING_DAY.isBetween(LOCAL_DATE.plusDays(1), LOCAL_DATE.plusDays(2)));
    }
}

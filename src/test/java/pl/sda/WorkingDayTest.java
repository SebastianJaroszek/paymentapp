package pl.sda;

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
    void isBetweenShouldReturnTrueBetweenDates() {
        //lewa granica przedziału
        assertTrue(WORKING_DAY.isBetween(LOCAL_DATE, LOCAL_DATE.plusDays(1)));
        //wnętrze
        assertTrue(WORKING_DAY.isBetween(LOCAL_DATE.minusDays(1), LOCAL_DATE.plusDays(1)));
        //prawa granica przedziału
        assertTrue(WORKING_DAY.isBetween(LOCAL_DATE.minusDays(1),LOCAL_DATE));

        /*LocalDate leftside = LocalDate.of(2018, 1, 8);
        LocalDate inside = LocalDate.of(2018, 1, 10);
        LocalDate rightside = LocalDate.of(2018, 1, 12);
        WorkingDay leftWorkingDay = new WorkingDay(leftside, WORKING_HOURS);
        WorkingDay centralWorkingDay = new WorkingDay(inside, WORKING_HOURS);
        WorkingDay rightWorkingDay = new WorkingDay(rightside, WORKING_HOURS);
        assertTrue(leftWorkingDay.isBetween(leftside, rightside));
        assertTrue(centralWorkingDay.isBetween(leftside, rightside));
        assertTrue(rightWorkingDay.isBetween(leftside, rightside));*/
    }

    @Test
    void isBetweenShouldReturnFalseOutsideDates() {
        //poza przedziałem
        assertFalse(WORKING_DAY.isBetween(LOCAL_DATE.plusDays(1), LOCAL_DATE.plusDays(2)));
        /*LocalDate fromDate = LocalDate.of(2018, 1, 8);
        LocalDate toDate = LocalDate.of(2018, 1, 12);
        LocalDate workingDayDate = LocalDate.of(2018, 1, 13);
        WorkingDay workingDay = new WorkingDay(workingDayDate, WORKING_HOURS);
        assertFalse(workingDay.isBetween(fromDate, toDate));*/
    }
}

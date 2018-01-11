package pl.sda;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WorkingDayTest {


    //working day jest wewnątrz 2 dat
    @Test
    void isBetweenShouldReturnTrueBetweenDates() {
        //lewa granica przedziału
        //wnętrze
        //prawa granica przedziału
        LocalDate leftside = LocalDate.of(2018, 1, 8);
        LocalDate inside = LocalDate.of(2018, 1, 10);
        LocalDate rightside = LocalDate.of(2018, 1, 12);
        WorkingDay leftWorkingDay = new WorkingDay(leftside, 8);
        WorkingDay centralWorkingDay = new WorkingDay(inside, 8);
        WorkingDay rightWorkingDay = new WorkingDay(rightside, 8);
        assertTrue(leftWorkingDay.isBetween(leftside, rightside));
        assertTrue(centralWorkingDay.isBetween(leftside, rightside));
        assertTrue(rightWorkingDay.isBetween(leftside, rightside));
    }

    @Test
    void isBetweenShouldReturnFalseOutsideDates() {
        //poza przedziałem
        LocalDate fromDate = LocalDate.of(2018, 1, 8);
        LocalDate toDate = LocalDate.of(2018, 1, 12);
        LocalDate workingDayDate = LocalDate.of(2018, 1, 13);
        WorkingDay workingDay = new WorkingDay(workingDayDate, 8);
        assertFalse(workingDay.isBetween(fromDate, toDate));
    }
}

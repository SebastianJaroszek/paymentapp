package pl.sda;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;

public class DateUtils {

    private static final Set<DayOfWeek> FREE_DAYS = new HashSet<>();

    static {
        FREE_DAYS.add(DayOfWeek.SATURDAY);
        FREE_DAYS.add(DayOfWeek.SUNDAY);
    }

    public static boolean isWorkingDay(LocalDate day) {
        return !FREE_DAYS.contains(day.getDayOfWeek());
    }

    /**
     * Znajduje poniedziałek dla danego piątku
     *
     * @param day piątek
     * @return pasujący poniedziałek
     */
    public static LocalDate findMonday(LocalDate day) {
        if (day.getDayOfWeek() != DayOfWeek.FRIDAY) {
            throw new IllegalStateException("Metoda findMonday() musi być wywołana dla piątku.");
        }
        return day.minusDays(4);
    }

    public static boolean isLastWorkingDayOfMonth(LocalDate day) {
        LocalDate lastDay = LocalDate.of(day.getYear(), 12, day.lengthOfMonth()); //from(day)
        while (FREE_DAYS.contains(lastDay.getDayOfWeek())) {
            lastDay = lastDay.minusDays(1);
        }
        return day.equals(lastDay);
    }

    public static boolean isLastWorkingDayOfYear(LocalDate day) {
        return day.getMonth() == Month.DECEMBER && isLastWorkingDayOfMonth(day);
    }

    public static boolean isSecondFriday(LocalDate day) {
        LocalDate secondFriday = findSecondFriday(day.getYear());
        int days = day.getDayOfYear() - secondFriday.getDayOfYear();
        return days % 14 == 0;
    }

    public static LocalDate findSecondFriday(int year) {
        LocalDate date = LocalDate.of(year, 1, 1);
        while (date.getDayOfWeek() != DayOfWeek.FRIDAY) {
            date = date.plusDays(1);
        }
        return date.plusDays(7);
    }
}

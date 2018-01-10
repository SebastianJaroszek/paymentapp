package pl.sda;

import lombok.Data;

import java.time.LocalDate;

@Data
public class WorkingDay {

    final private LocalDate date;
    final private int hours;

    /**
     * Metoda sprawdza, czy workind day znajduje się w podanym przedziale (włącznie)
     *
     * @param fromDate początek przedziału
     * @param toDate   koniec przedziału
     * @return true jeżeli working day znajduje się w podanym okresie
     */
    public boolean isBetween(LocalDate fromDate, LocalDate toDate) {
        boolean result = false;
        if ((date.isAfter(fromDate) || date.isEqual(fromDate))
                && date.isBefore(toDate) || date.isEqual(toDate)) {
            result = true;
        }
        return result;

        //return fromDate.compareTo(date) <= 0 && date.compareTo(toDate) <= 0;
    }
}

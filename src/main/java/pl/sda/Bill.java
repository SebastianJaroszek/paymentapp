package pl.sda;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Bill {
    private final LocalDate date;
    private final BigDecimal value;

    /**
     * Metoda sprawdza, czy workind day znajduje się w podanym przedziale (włącznie)
     *
     * @param fromDate początek przedziału
     * @param toDate   koniec przedziału
     * @return true jeżeli working day znajduje się w podanym okresie
     */
    public boolean isBetween(LocalDate fromDate, LocalDate toDate){
        return fromDate.compareTo(date) <= 0 && date.compareTo(toDate) <= 0;
    }
}

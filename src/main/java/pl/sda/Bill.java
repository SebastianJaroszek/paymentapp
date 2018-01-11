package pl.sda;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Bill {
    private final LocalDate date;
    private final BigDecimal value;
}

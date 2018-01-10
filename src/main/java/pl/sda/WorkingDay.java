package pl.sda;

import lombok.Data;

import java.time.LocalDate;

@Data
public class WorkingDay {

    final private LocalDate date;
    final private int hours;

}

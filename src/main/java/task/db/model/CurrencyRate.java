package task.db.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyRate {
    private LocalDateTime dateTime;
    private String fromCurrency;
    private String toCurrency;
    private double rate;
}

package task.db.mapper;

import task.db.model.CurrencyRate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CurrencyRateMapper {
    public static CurrencyRate map(String line) {
        String[] parts = line.split(";");
        LocalDateTime dateTime = LocalDateTime.parse(parts[0], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return new CurrencyRate(dateTime, parts[1], parts[2], Double.parseDouble(parts[3]));
    }
}

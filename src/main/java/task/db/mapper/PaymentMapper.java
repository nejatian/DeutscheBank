package task.db.mapper;


import task.db.model.Payment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PaymentMapper {
    public static Payment map(String line) {
        String[] parts = line.split(";");
        LocalDateTime dateTime = LocalDateTime.parse(parts[0], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return new Payment(dateTime, parts[1], parts[2], Double.parseDouble(parts[3]));
    }
}

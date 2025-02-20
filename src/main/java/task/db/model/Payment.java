package task.db.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private LocalDateTime dateTime;
    private String company;
    private String currency;
    private double amount;
}

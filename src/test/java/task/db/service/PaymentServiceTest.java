package task.db.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import task.db.model.Payment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PaymentServiceTest {
    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private List<Payment> payments;

    @Mock
    private Map<String, Double> latestRates;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        payments = new ArrayList<>();
        latestRates = new HashMap<>();

        payments.add(new Payment(LocalDateTime.of(2025, 2, 14, 10, 0), "Company A", "EUR", -10001));
        payments.add(new Payment(LocalDateTime.of(2025, 2, 14, 11, 30), "Company A", "EUR", 15002));
        payments.add(new Payment(LocalDateTime.of(2025, 2, 14, 12, 45), "Company B", "USD", 200));
        payments.add(new Payment(LocalDateTime.of(2025, 2, 14, 13, 0), "Company C", "GBP", 300));

        latestRates.put("USD:EUR", 0.9);
        latestRates.put("GBP:EUR", 1.2);
    }

    @Test
    void should_get_highest_eur_value() {
        Double highestEur = paymentService.getHighestEur(LocalDate.of(2025, 2, 14));
        assertThat(highestEur).isEqualTo(15002);
    }

    @Test
    void should_get_lowest_eur_value() {
        Double lowestEur = paymentService.getLowestEur(LocalDate.of(2025, 2, 14));
        assertThat(lowestEur).isEqualTo(-10001);
    }

    @Test
    void should_get_transaction_volume() {
        Double transactionVolume = paymentService.getTransactionVolume(LocalDate.of(2025, 2, 14));
        assertThat(transactionVolume).isEqualTo(15002 - 10001 + (200 * 0.9) + (300 * 1.2));
    }

    @Test
    void should_get_outstanding_per_company() {
        Map<String, Double> outstandingPerCompany = paymentService.getOutstandingPerCompany(LocalDate.of(2025, 2, 14));
        assertThat(outstandingPerCompany).containsEntry("Company A", 5001.0);
    }

    @Test
    void should_get_outstanding_per_currency() {
        Map<String, Double> outstandingPerCurrency = paymentService.getOutstandingPerCurrency(LocalDate.of(2025, 2, 14));
        assertThat(outstandingPerCurrency).containsEntry("EUR", 5001.0);
    }
}

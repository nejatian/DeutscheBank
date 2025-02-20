package task.db.service;

import org.springframework.stereotype.Service;
import task.db.exception.FileProcessingException;
import task.db.mapper.CurrencyRateMapper;
import task.db.mapper.PaymentMapper;
import task.db.model.CurrencyRate;
import task.db.model.Payment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PaymentService {
    private final List<Payment> payments;
    private final Map<String, Double> latestRates;

    private static final String PAYMENTS_FILE = "data/payments.txt";
    private static final String CURRENCY_RATES_FILE = "data/currency_rates.txt";

    public PaymentService() {
        this.payments = new ArrayList<>();
        this.latestRates = new HashMap<>();
        loadPayments();
        loadCurrencyRates();
    }

    private void loadPayments() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(PAYMENTS_FILE);
             BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)));
             Stream<String> lines = reader.lines()) {
            this.payments.addAll(lines.map(PaymentMapper::map).toList());
        } catch (IOException | NullPointerException e) {
            throw new FileProcessingException("Error loading payments file", e);
        }
    }

    private void loadCurrencyRates() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(CURRENCY_RATES_FILE);
             BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)));
             Stream<String> lines = reader.lines()) {
            List<CurrencyRate> rates = lines.map(CurrencyRateMapper::map).toList();
            this.latestRates.putAll(rates.stream()
                    .collect(Collectors.toMap(
                            r -> r.getFromCurrency() + ":" + r.getToCurrency(),
                            CurrencyRate::getRate,
                            (r1, r2) -> r2
                    )));
        } catch (IOException | NullPointerException e) {
            throw new FileProcessingException("Error loading currency rates file", e);
        }
    }

    public Double getHighestEur(LocalDate date) {
        return getConvertedPayments(date).stream().max(Double::compare).orElse(0.0);
    }

    public Double getLowestEur(LocalDate date) {
        return getConvertedPayments(date).stream().min(Double::compare).orElse(0.0);
    }

    public Double getTransactionVolume(LocalDate date) {
        return getConvertedPayments(date).stream().mapToDouble(Double::doubleValue).sum();
    }

    public Map<String, Double> getOutstandingPerCompany(LocalDate date) {
        return filterPaymentsByDate(date).stream()
                .collect(Collectors.groupingBy(Payment::getCompany, Collectors.summingDouble(p -> Optional.ofNullable(convertToEUR(p)).orElse(0.0))));
    }

    public Map<String, Double> getOutstandingPerCurrency(LocalDate date) {
        return filterPaymentsByDate(date).stream()
                .collect(Collectors.groupingBy(Payment::getCurrency, Collectors.summingDouble(p -> Optional.ofNullable(convertToEUR(p)).orElse(0.0))));
    }

    private List<Double> getConvertedPayments(LocalDate date) {
        return filterPaymentsByDate(date).stream()
                .map(this::convertToEUR)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private List<Payment> filterPaymentsByDate(LocalDate date) {
        return payments.stream()
                .filter(p -> p.getDateTime().toLocalDate().equals(date))
                .collect(Collectors.toList());
    }

    private Double convertToEUR(Payment payment) {
        if (payment.getCurrency().equals("EUR")) {
            return payment.getAmount();
        }
        String key = payment.getCurrency() + ":EUR";
        return latestRates.containsKey(key) ? payment.getAmount() * latestRates.get(key) : null;
    }
}

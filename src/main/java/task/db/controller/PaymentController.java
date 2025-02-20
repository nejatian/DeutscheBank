package task.db.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import task.db.service.PaymentService;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/highest-eur/{date}")
    public Double getHighestEur(@PathVariable String date) {
        return paymentService.getHighestEur(LocalDate.parse(date));
    }

    @GetMapping("/lowest-eur/{date}")
    public Double getLowestEur(@PathVariable String date) {
        return paymentService.getLowestEur(LocalDate.parse(date));
    }

    @GetMapping("/outstanding-per-company/{date}")
    public Map<String, Double> getOutstandingPerCompany(@PathVariable String date) {
        return paymentService.getOutstandingPerCompany(LocalDate.parse(date));
    }

    @GetMapping("/transaction-volume/{date}")
    public Double getTransactionVolume(@PathVariable String date) {
        return paymentService.getTransactionVolume(LocalDate.parse(date));
    }

    @GetMapping("/outstanding-per-currency/{date}")
    public Map<String, Double> getOutstandingPerCurrency(@PathVariable String date) {
        return paymentService.getOutstandingPerCurrency(LocalDate.parse(date));
    }
}

package task.db.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PaymentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_get_highest_eur_value() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/payments/highest-eur/2025-02-14"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("15002.0"));
    }

    @Test
    void should_get_lowest_eur_value() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/payments/lowest-eur/2025-02-14"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("-10001.0"));
    }

    @Test
    void should_get_transaction_volume() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/payments/transaction-volume/2025-02-14"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("5541.0"));
    }

    @Test
    void should_get_outstanding_per_company() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/payments/outstanding-per-company/2025-02-14"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.['Company A']").value(5001.0));
    }

    @Test
    void should_get_outstanding_per_currency() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/payments/outstanding-per-currency/2025-02-14"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.['EUR']").value(5001.0));
    }
}

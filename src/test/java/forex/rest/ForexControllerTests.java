package forex.rest;

import forex.db.ForexRepository;
import forex.enums.CURRENCY_CODE;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ForexControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ForexRepository forexRepositoryMock;

    @Test
    public void tradingShouldNotBePossible() throws Exception {

        when(forexRepositoryMock.findById(CURRENCY_CODE.CAD)).thenReturn(Optional.of(CommonTestData.getForexEntityCAD()));
        when(forexRepositoryMock.findById(CURRENCY_CODE.AUD)).thenReturn(Optional.of(CommonTestData.getForexEntityAUD()));

        mockMvc.perform(get("/api/cutoffTime").param("toCurrency", "CAD")
                        .param("fromCurrency", "AUD").param("dateOfConversion", LocalDate.now().toString()))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.cutoffTime").value("00:00:00"));
    }

    @Test
    public void tradingIsTimeRestricted() throws Exception {

        when(forexRepositoryMock.findById(CURRENCY_CODE.CAD)).thenReturn(Optional.of(CommonTestData.getForexEntityCAD()));
        when(forexRepositoryMock.findById(CURRENCY_CODE.AUD)).thenReturn(Optional.of(CommonTestData.getForexEntityAUD()));

        mockMvc.perform(get("/api/cutoffTime").param("toCurrency", "CAD")
                        .param("fromCurrency", "AUD").param("dateOfConversion", LocalDate.now().plusDays(1).toString()))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.cutoffTime").value("14:00:00"));
    }

    @Test
    public void tradingShouldBeAvailableAllDay() throws Exception {

        when(forexRepositoryMock.findById(CURRENCY_CODE.CAD)).thenReturn(Optional.of(CommonTestData.getForexEntityCAD()));
        when(forexRepositoryMock.findById(CURRENCY_CODE.AUD)).thenReturn(Optional.of(CommonTestData.getForexEntityAUD()));

        mockMvc.perform(get("/api/cutoffTime").param("toCurrency", "CAD")
                        .param("fromCurrency", "AUD").param("dateOfConversion", LocalDate.now().plusDays(2).toString()))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.cutoffTime").value("23:59:59.999999999"));
    }

    @Test
    public void requestWithInvalidCurrency() throws Exception {

        mockMvc.perform(get("/api/cutoffTime").param("toCurrency", "AAA")
                        .param("fromCurrency", "AUD").param("dateOfConversion", LocalDate.now().plusDays(2).toString()))
                .andDo(print()).andExpect(status().is(400))
                .andExpect(content().string("Unsupported or invalid currency format: AAA"));
    }

    @Test
    public void requestWithInvalidDateOfConversion() throws Exception {

        mockMvc.perform(get("/api/cutoffTime").param("toCurrency", "CAD")
                        .param("fromCurrency", "AUD").param("dateOfConversion", "2021-11-111"))
                .andDo(print()).andExpect(status().is(400))
                .andExpect(content().string("Field 'dateOfConversion' has required format yyyy-MM-DD"));
    }
    @Test
    public void requestWithMissingDateOfConversion() throws Exception {

        mockMvc.perform(get("/api/cutoffTime").param("toCurrency", "CAD")
                        .param("fromCurrency", "AUD"))
                .andDo(print()).andExpect(status().is(400))
                .andExpect(content().string("Required parameter: 'dateOfConversion' is missing"));
    }

    @Test
    public void requestWithMissingCurrency() throws Exception {

        mockMvc.perform(get("/api/cutoffTime").param("toCurrency", "CAD")
                        .param("dateOfConversion", "2021-11-111"))
                .andDo(print()).andExpect(status().is(400))
                .andExpect(content().string("Required parameter: 'fromCurrency' is missing"));
    }

    @Test
    public void requestWithSameCurrency() throws Exception {

        mockMvc.perform(get("/api/cutoffTime").param("toCurrency", "AUD")
                        .param("fromCurrency", "AUD").param("dateOfConversion", LocalDate.now().plusDays(2).toString()))
                .andDo(print()).andExpect(status().is(400))
                .andExpect(content().string("Not possible to trade same currency"));
    }

    @Test
    public void requestWithDateOfConversionYesterday() throws Exception {

        mockMvc.perform(get("/api/cutoffTime").param("toCurrency", "CAD")
                        .param("fromCurrency", "AUD").param("dateOfConversion", LocalDate.now().minusDays(2).toString()))
                .andDo(print()).andExpect(status().is(400))
                .andExpect(content().string("Not possible to trade in the past"));
    }

    @Test
    public void requestWithMissingCurrencyInDB() throws Exception {

        mockMvc.perform(get("/api/cutoffTime").param("toCurrency", "DKK")
                        .param("fromCurrency", "AUD").param("dateOfConversion", LocalDate.now().plusDays(2).toString()))
                .andDo(print()).andExpect(status().is(404))
                .andExpect(content().string("Currency AUD was not found"));
    }
}

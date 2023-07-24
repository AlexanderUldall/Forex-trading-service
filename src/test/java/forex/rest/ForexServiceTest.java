package forex.rest;

import forex.db.DBService;
import forex.db.ForexEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ForexServiceTest {

    @Mock
    private DBService dbService = mock(DBService.class);

    private ForexService forexService = new ForexService(dbService);

    @Test
    public void timeShouldBe10_30() {

        LocalTime localtimeTo = LocalTime.of(10, 30, 0);
        LocalTime localtimeFrom = LocalTime.of(14, 30, 0);

        LocalTime localTime = forexService.extractCutoffTime(localtimeTo, localtimeFrom);

        Assertions.assertEquals(LocalTime.of(10, 30, 0), localTime);
    }

    @Test
    public void timeShouldBe23_59() {

        LocalTime localtimeTo = CommonTestData.ALWAYS_POSSIBLE_TIME;
        LocalTime localtimeFrom = CommonTestData.ALWAYS_POSSIBLE_TIME;

        LocalTime localTime = forexService.extractCutoffTime(localtimeTo, localtimeFrom);

        Assertions.assertEquals(LocalTime.of(23, 59, 59, 999999999), localTime);
    }

    @Test
    public void timeShouldBe12_30() {

        LocalTime localtimeTo = LocalTime.of(12, 30, 00);
        LocalTime localtimeFrom = CommonTestData.ALWAYS_POSSIBLE_TIME;

        LocalTime localTime = forexService.extractCutoffTime(localtimeTo, localtimeFrom);

        Assertions.assertEquals(LocalTime.of(12, 30, 0), localTime);
    }

    @Test
    public void timeShouldBe00_00() {

        LocalTime localtimeTo = LocalTime.of(12, 30, 0);
        LocalTime localtimeFrom = CommonTestData.NEVER_POSSIBLE_TIME;

        LocalTime localTime = forexService.extractCutoffTime(localtimeTo, localtimeFrom);

        Assertions.assertEquals(LocalTime.of(0, 0, 0), localTime);
    }

    @Test
    public void methodExtractCutoffTimeShouldBeCalled() {

        ForexEntity toForexEntity = CommonTestData.getForexEntityCAD();
        ForexEntity fromForexEntity = CommonTestData.getForexEntityAUD();
        LocalDate dateOfConversion = LocalDate.now();
        LocalDate today = LocalDate.now();

        ForexService forexServiceSpy = spy(forexService);

        LocalTime localTime = forexServiceSpy.getCutoffTime(toForexEntity, fromForexEntity, dateOfConversion, today);

        Assertions.assertEquals(LocalTime.of(0, 0, 0), localTime);
        verify(forexServiceSpy, times(1)).extractCutoffTime(any(), any());
    }
}
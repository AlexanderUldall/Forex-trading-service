package forex.rest;

import forex.enums.CURRENCY_CODE;
import forex.exceptions.CurrencyNotFoundInDBException;
import lombok.AllArgsConstructor;
import forex.db.DBService;
import forex.db.ForexEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@AllArgsConstructor
public class ForexService {

    @Autowired
    private DBService dbService;

    public LocalTime getCutoffTime(CURRENCY_CODE to, CURRENCY_CODE from, LocalDate dateOfConversion, LocalDate today) throws CurrencyNotFoundInDBException {

        ForexEntity fromForexEntity = dbService.getForex(from);
        ForexEntity toForexEntity = dbService.getForex(to);

        return getCutoffTime(toForexEntity, fromForexEntity, dateOfConversion, today);
    }

    protected LocalTime getCutoffTime(ForexEntity toForexEntity, ForexEntity fromForexEntity, LocalDate dateOfConversion, LocalDate today) {
        if (today.equals(dateOfConversion)) {
            return extractCutoffTime(toForexEntity.getTodayTime(),
                    fromForexEntity.getTodayTime());
        } else if (today.plusDays(1).equals(dateOfConversion)) {
            return extractCutoffTime(toForexEntity.getTomorrowTime(),
                    fromForexEntity.getTomorrowTime());
        } else {
            return extractCutoffTime(toForexEntity.getAfterTomorrowTime(),
                    fromForexEntity.getAfterTomorrowTime());
        }
    }

    protected LocalTime extractCutoffTime(LocalTime localtimeTo, LocalTime localtimeFrom) {
        if (localtimeTo.isAfter(localtimeFrom)) {
            return localtimeFrom;
        } else {
            return localtimeTo;
        }
    }
}

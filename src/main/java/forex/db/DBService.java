package forex.db;

import forex.enums.CURRENCY_CODE;
import forex.exceptions.CurrencyNotFoundInDBException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class DBService {

    @Autowired
    private ForexRepository forexRepository;

    @Autowired
    private CacheManager cacheManager; // Not needed, just here for easy access of cache during runtime

    @Cacheable("forex")
    public ForexEntity getForex(CURRENCY_CODE CURRENCYCOODE) throws CurrencyNotFoundInDBException {
        log.info("Getting currency from DB: " + CURRENCYCOODE);
        return forexRepository.findById(CURRENCYCOODE).orElseThrow(() -> {
            log.error("Currency code '" + CURRENCYCOODE + "' could not be found in the DB");
            return new CurrencyNotFoundInDBException(CURRENCYCOODE);
        });
    }
}

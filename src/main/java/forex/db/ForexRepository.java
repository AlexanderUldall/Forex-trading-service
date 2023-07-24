package forex.db;

import forex.enums.CURRENCY_CODE;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForexRepository
        extends CrudRepository<ForexEntity, CURRENCY_CODE> {
}

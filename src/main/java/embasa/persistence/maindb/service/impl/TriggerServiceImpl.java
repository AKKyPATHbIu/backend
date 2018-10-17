package embasa.persistence.maindb.service.impl;

import embasa.persistence.BaseJdbcServiceImpl;
import embasa.persistence.maindb.model.Trigger;
import embasa.persistence.maindb.service.TriggerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional("mainDBTransactionManager")
/** Реалізація сервісу тригерів. */
public class TriggerServiceImpl extends BaseJdbcServiceImpl<Trigger> implements TriggerService {
}

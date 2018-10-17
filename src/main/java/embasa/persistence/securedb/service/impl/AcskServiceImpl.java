package embasa.persistence.securedb.service.impl;

import embasa.persistence.BaseJdbcServiceImpl;
import embasa.persistence.securedb.model.Acsk;
import embasa.persistence.securedb.service.AcskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional("secureDBTransactionManager")
/** Реалізація сервісу АЦСК. */
public class AcskServiceImpl extends BaseJdbcServiceImpl<Acsk> implements AcskService {
}

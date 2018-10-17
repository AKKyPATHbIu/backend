package embasa.persistence.maindb.service.impl;

import embasa.persistence.BaseJdbcServiceImpl;
import embasa.persistence.maindb.model.WfStatus;
import embasa.persistence.maindb.service.WfStatusService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional("mainDBTransactionManager")
/** Реалізація сервісу статуса workflow. */
public class WfStatusServiceImpl extends BaseJdbcServiceImpl<WfStatus> implements WfStatusService {
}

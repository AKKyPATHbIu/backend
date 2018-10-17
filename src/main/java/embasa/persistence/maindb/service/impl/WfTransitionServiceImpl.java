package embasa.persistence.maindb.service.impl;

import embasa.persistence.BaseJdbcServiceImpl;
import embasa.persistence.maindb.model.WfTransition;
import embasa.persistence.maindb.service.WfTransitionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional("mainDBTransactionManager")
/** Реалізація сервіса переходу статуса workflow. */
public class WfTransitionServiceImpl extends BaseJdbcServiceImpl<WfTransition> implements WfTransitionService {
}

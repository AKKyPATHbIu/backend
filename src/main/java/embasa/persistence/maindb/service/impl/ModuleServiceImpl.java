package embasa.persistence.maindb.service.impl;

import embasa.persistence.BaseJdbcServiceImpl;
import embasa.persistence.maindb.model.Module;
import embasa.persistence.maindb.service.ModuleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional("mainDBTransactionManager")
/** Реалізація сервіса модулей системи. */
public class ModuleServiceImpl extends BaseJdbcServiceImpl<Module> implements ModuleService {
}

package embasa.persistence.maindb.service.impl;

import embasa.persistence.BaseJdbcServiceImpl;
import embasa.persistence.maindb.model.Validator;
import embasa.persistence.maindb.service.ValidatorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional("mainDBTransactionManager")
/** Реалізація сервісу валідатора. */
public class ValidatorServiceImpl extends BaseJdbcServiceImpl<Validator> implements ValidatorService {
}

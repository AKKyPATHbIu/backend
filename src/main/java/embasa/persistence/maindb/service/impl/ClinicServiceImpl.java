package embasa.persistence.maindb.service.impl;

import embasa.persistence.BaseJdbcServiceImpl;
import embasa.persistence.maindb.model.Clinic;
import embasa.persistence.maindb.service.ClinicService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional("mainDBTransactionManager")
public class ClinicServiceImpl extends BaseJdbcServiceImpl<Clinic> implements ClinicService {
}

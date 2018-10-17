package embasa.persistence.securedb.service.impl;

import embasa.persistence.BaseJdbcServiceImpl;
import embasa.persistence.securedb.model.Group;
import embasa.persistence.securedb.service.GroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional("secureDBTransactionManager")
public class GroupServiceImpl extends BaseJdbcServiceImpl<Group> implements GroupService {
}

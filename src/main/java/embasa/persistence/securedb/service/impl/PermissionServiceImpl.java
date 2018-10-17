package embasa.persistence.securedb.service.impl;

import embasa.persistence.BaseJdbcServiceImpl;
import embasa.persistence.securedb.model.Permission;
import embasa.persistence.securedb.repository.PermissionRepository;
import embasa.persistence.securedb.service.PermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional("secureDBTransactionManager")
public class PermissionServiceImpl extends BaseJdbcServiceImpl<Permission> implements PermissionService {

    @Override
    public List<Permission> findByUser(Long userId) {
        return ((PermissionRepository) repository).findByUser(userId);
    }

    @Override
    public List<Permission> findByRole(Long roleId) {
        return ((PermissionRepository) repository).findByRole(roleId);
    }
}

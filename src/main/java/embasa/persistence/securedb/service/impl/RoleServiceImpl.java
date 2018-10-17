package embasa.persistence.securedb.service.impl;

import embasa.persistence.BaseJdbcServiceImpl;
import embasa.persistence.securedb.model.Role;
import embasa.persistence.securedb.repository.RoleRepository;
import embasa.persistence.securedb.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional("secureDBTransactionManager")
public class RoleServiceImpl extends BaseJdbcServiceImpl<Role> implements RoleService {

    @Override
    public List<Role> findByUser(Long userId) {
        return ((RoleRepository) repository).findByUser(userId);
    }

    @Override
    public List<Role> findByPermission(Long permissionId) {
        return ((RoleRepository) repository).findByPermission(permissionId);
    }

    @Override
    public List<Role> findByParentRole(Long roleId) {
        return ((RoleRepository) repository).findByParentRole(roleId);
    }
}
package embasa.persistence.securedb.repository;

import embasa.persistence.BaseJdbcRepository;
import embasa.persistence.securedb.model.Group;
import embasa.persistence.securedb.model.Permission;
import embasa.persistence.securedb.model.Role;
import embasa.persistence.securedb.model.User;

import java.util.List;

/** Інтерфейс репозиторію груп користувачів. */
public interface GroupRepository extends BaseJdbcRepository<Group, Long> {

    /**
     * Знайти групи, з якими пов'язаний користувач
     * @param user користувач
     * @return групи, з якими пов'язаний користувач
     */
    List<Group> findByUser(User user);

    /**
     * Знайти групи, з якими пов'язана роль
     * @param role роль
     * @return групи, з якими пов'язана роль
     */
    List<Group> findByRole(Role role);

    /**
     * Знайти групи, з якими пов'язаний дозвіл
     * @param permission дозвіл
     * @return групи, з якими пов'язаний дозвіл
     */
    List<Group> findByPermission(Permission permission);
}

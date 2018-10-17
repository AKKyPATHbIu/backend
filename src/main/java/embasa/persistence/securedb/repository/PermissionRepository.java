package embasa.persistence.securedb.repository;

import embasa.persistence.BaseJdbcRepository;
import embasa.persistence.securedb.model.Permission;

import java.util.List;

/** Інтерфейс репозиторія дозволів. */
public interface PermissionRepository extends BaseJdbcRepository<Permission, Long> {

    /**
     * Отримати список дозволів для користувача
     * @param userId ідетифікатор користувача
     * @return список дозволів для користувача
     */
    List<Permission> findByUser(Long userId);

    /**
     * Отримати список дозволів для ролі
     * @param roleId ідентифікатор ролі
     * @return список дозволів для ролі
     */
    List<Permission> findByRole(Long roleId);
}

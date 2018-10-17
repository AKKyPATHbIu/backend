package embasa.persistence.securedb.service;

import embasa.persistence.BaseJdbcService;
import embasa.persistence.securedb.model.Permission;

import java.util.List;

/** Інтерфейс сервіса дозволів користувача. */
public interface PermissionService extends BaseJdbcService<Permission, Long> {

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

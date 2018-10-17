package embasa.persistence.securedb.service;

import embasa.persistence.BaseJdbcService;
import embasa.persistence.securedb.model.Role;

import java.util.List;

/** Сервіс ролей користувачів. */
public interface RoleService extends BaseJdbcService<Role, Long> {

    /**
     * Отримати ролі користувача
     * @param userId ідентифікатор користувача
     * @return ролі користувача
     */
    List<Role> findByUser(Long userId);

    /**
     * Отримати всі ролі, з якими повязаний дозвіл
     * @param permissionId ідентифікатор дозволу
     * @return всі ролі, з якими повязаний дозвіл
     */
    List<Role> findByPermission(Long permissionId);

    /**
     * Отримати всі дочірні ролі
     * @param roleId батьківська роль
     * @return всі дочірні ролі
     */
    List<Role> findByParentRole(Long roleId);
}

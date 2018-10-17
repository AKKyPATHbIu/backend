package embasa.persistence.securedb.repository.impl;

import embasa.persistence.securedb.model.Role;
import embasa.persistence.securedb.repository.RoleRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Реалізація репозиторія ролей. */
public class RoleRepositoryImpl extends SecureDBJdbcRepositoryInsertImpl<Role, Long> implements RoleRepository {

    /** Ім'я таблиці. */
    private static final String TABLE_NAME = "roles";

    /** Скрипт отримання всіх ролей користувача. */
    private static String FIND_BY_USER_SQL = buildFindByUserSql();

    /** Скрипт отримання всіх ролей для дозволу. */
    private static String FIND_BY_PERMISSION_SQL = buildFindByPermissionSql();

    /** Скрипт отримання всіх ролей для батьківської ролі. */
    private static String FIND_BY_PARENT_ROLE_SQL = buildFindByParentRoleSql();

    /** Конструктор за замовчанням. */
    public RoleRepositoryImpl() {
        super(TABLE_NAME);
    }

    /**
     * Отримати скрипт пошуку ролей користувача
     * @return скрипт пошуку ролей користувача
     */
    private static String buildFindByUserSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT r.*");
        sql.append(String.format(" FROM %s r", TABLE_NAME));
        sql.append(" INNER JOIN user_roles ur ON ur.role_id = r.id");
        sql.append(" WHERE ur.user_id = ?");
        return sql.toString();
    }

    /**
     * Отримати скрипт пошуку ролей дозволу
     * @return скрипт пошуку ролей дозволу
     */
    private static String buildFindByPermissionSql() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT r.*");
        sql.append(String.format(" FROM %s r", TABLE_NAME));
        sql.append(" INNER JOIN role_permissions rp ON rp.role_id = r.id");
        sql.append(" WHERE rp.permission_id = ?");
        return sql.toString();
    }

    /**
     * Отримати скрипт пошуку ролей батьківскої ролі
     * @return скрипт пошуку ролей батьківскої ролі
     */
    private static String buildFindByParentRoleSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT r.*");
        sql.append(String.format(" FROM %s r", TABLE_NAME));
        sql.append(" INNER JOIN role_roles rr ON rr.child_role_id = r.id");
        sql.append(" WHERE rr.parent_role_id = ?");
        return sql.toString();
    }

    @Override
    public void save(Role p) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", p.getName());
        params.put("description", p.getDescription());
        Long id = jdbcInsert.executeAndReturnKey(params).longValue();
        p.setId(id);
    }

    @Override
    public Role mapRow(ResultSet resultSet, int i) throws SQLException {
        Role role = new Role();
        role.setId(resultSet.getLong("id"));
        role.setName(resultSet.getString("name"));
        role.setDescription(resultSet.getString("description"));
        return role;
    }

    @Override
    public List<Role> findByUser(Long userId) {
        return jdbcTemplate.query(FIND_BY_USER_SQL, new Object[] { userId }, new int[] { Types.BIGINT }, this);
    }

    @Override
    public List<Role> findByPermission(Long permissionId) {
        return jdbcTemplate.query(FIND_BY_PERMISSION_SQL, new Object[] { permissionId }, new int[] { Types.BIGINT }, this);
    }

    @Override
    public List<Role> findByParentRole(Long roleId) {
        return jdbcTemplate.query(FIND_BY_PARENT_ROLE_SQL, new Object[] { roleId }, new int[] { Types.BIGINT }, this);
    }
}

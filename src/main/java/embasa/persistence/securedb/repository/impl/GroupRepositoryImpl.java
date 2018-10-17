package embasa.persistence.securedb.repository.impl;

import embasa.enums.DataBase;
import embasa.persistence.securedb.model.Group;
import embasa.persistence.securedb.model.Permission;
import embasa.persistence.securedb.model.Role;
import embasa.persistence.securedb.model.User;
import embasa.persistence.securedb.repository.GroupRepository;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Реалізація репозиторія груп користувачів. */
public class GroupRepositoryImpl extends SecureDBJdbcRepositoryInsertImpl<Group, Long>  implements GroupRepository {

    /** Ім'я таблиці сутності.*/
    private static final String TABLE_NAME = "groups";

    /** Sql-запит оновлення запису. */
    private static final String UPDATE = String.format("UPDATE %s SET name = ?, descr = ? WHERE id = ?", TABLE_NAME);

    /** Sql-запит отримання всіх груп, з якими пов'язаний користувач. */
    private static final String FIND_BY_USER = buildFindByUserSql();

    /** Sql-запит отримання всіх груп, з якими пов'язана роль. */
    private static final String FIND_BY_ROLE = buildFindByRoleSql();

    /** Sql-запит отримання всіх груп, з якими пов'язаний дозвіл. */
    private static final String FIND_BY_PERMISSION = buildFindByPermissionSql();

    /** Конструктор за замовчанням. */
    public GroupRepositoryImpl() {
        super(TABLE_NAME);
    }

    /**
     * Отримати скрипт пошуку всіх груп, з якими пов'язаний користувач
     * @return скрипт пошуку всіх груп, з якими пов'язаний користувач
     */
    private static String buildFindByUserSql() {
        StringBuilder sql = getSqlBuilder();
        sql.append(" INNER JOIN user_groups ug ON ug.group_id = g.id");
        sql.append(" WHERE ug.user_id = ?");
        return sql.toString();
    }

    /**
     * Отримати скрипт пошуку всіх груп, з якими пов'язанана роль
     * @return скрипт пошуку всіх груп, з якими пов'язанана роль
     */
    private static String buildFindByRoleSql() {
        StringBuilder sql = getSqlBuilder();
        sql.append(" INNER JOIN group_roles gr ON gr.group_id = g.id");
        sql.append(" WHERE gr.role_id = ?");
        return sql.toString();
    }

    /**
     * Отримати скрипт пошуку всіх груп, з якими пов'язаний дозвіл
     * @return скрипт пошуку всіх груп, з якими пов'язаний дозвіл
     */
    private static String buildFindByPermissionSql() {
        StringBuilder sql = getSqlBuilder();
        sql.append(" INNER JOIN group_permissions gp ON gp.group_id = g.id");
        sql.append(" WHERE gp.permission_id = ?");
        return sql.toString();
    }

    /**
     * Отримати StringBuilder з початковим запитом
     * @return StringBuilder з початковим запитом
     */
    public static StringBuilder getSqlBuilder() {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(" SELECT *");
        sqlBuilder.append(String.format("FROM %s g", TABLE_NAME));
        return sqlBuilder;
    }

    @Override
    public void save(Group p) {
        Map<String, Object> params = new HashMap<> ();
        params.put("name", p.getName());
        params.put("descr", p.getDescription());
        Long id = jdbcInsert.executeAndReturnKey(params).longValue();
        p.setId(id);
    }

    @Override
    public void update(Group p) {
        jdbcTemplate.update(UPDATE, p.getId());
    }

    @Override
    public Group mapRow(ResultSet resultSet, int i) throws SQLException {
        Group group = new Group();
        group.setId(resultSet.getLong("id"));
        group.setName(resultSet.getString("name"));
        group.setDescription(resultSet.getString("descr"));
        return group;
    }

    @Override
    public List<Group> findByUser(User user) {
        return findGroupsFor(FIND_BY_USER, user.getId());
    }

    @Override
    public List<Group> findByRole(Role role) {
        return findGroupsFor(FIND_BY_ROLE, role.getId());
    }

    @Override
    public List<Group> findByPermission(Permission permission) {
        return findGroupsFor(FIND_BY_PERMISSION, permission.getId());
    }

    /**
     * Отримати список груп для запиту з параметром
     * @param sql запит
     * @param id параметр запиту
     * @return список груп для запиту з параметром
     */
    private List<Group> findGroupsFor(String sql, Long id) {
        List<Group> result = new ArrayList<>();
        SqlRowSet srs = jdbcTemplate.queryForRowSet(sql, id);
        ResultSet rs = ((ResultSetWrappingSqlRowSet) srs).getResultSet();
        try {
            if (rs.next()) {
                while (!rs.isAfterLast()) {
                    result.add(mapRow(rs, 0));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }
}

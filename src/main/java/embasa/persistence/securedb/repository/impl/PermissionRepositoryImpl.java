package embasa.persistence.securedb.repository.impl;

import embasa.enums.DataBase;
import embasa.i18n.LanguageHolder;
import embasa.persistence.securedb.model.Permission;
import embasa.persistence.securedb.repository.PermissionRepository;
import embasa.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Реалізація репозиторія дозволів. */
public class PermissionRepositoryImpl extends SecureDBJdbcRepositoryLocalizableImpl<Permission, Long> implements PermissionRepository {

    /** Ім'я таблиці. */
    private static final String TABLE_NAME = "permissions";

    /** Скрипт отримання всіх записів. */
    private static final String FIND_ALL = buildFindAllSql() + " ORDER BY p.id";

    /** Скрипт отримання запису за ідентифікатором. */
    private static final String FIND_BY_ID = buildFindAllSql() + " WHERE p.id = ? ORDER BY p.id";

    /** Скрипт отриманя дозволів користувача. */
    private static final String FIND_BY_USER = buildFindAllSql() + " INNER JOIN user_permissions up ON up.permission_id = p.id" +
            " WHERE up.user_id = ? ORDER BY p.id";

    /** Скрипт отриманя дозволів ролі. */
    private static final String FIND_BY_ROLE = buildFindAllSql() + " INNER JOIN role_permissions rp ON rp.permission_id = p.id" +
            " WHERE rp.role_id = ? ORDER BY p.id";

    /** Об'єкт для додавання запису в таблицю. */
    private SimpleJdbcInsert jdbcInsert;

    /** Конструктор за замовчанням. */
    public PermissionRepositoryImpl() {
        super(TABLE_NAME);
    }

    /** Утримувач мов. */
    private LanguageHolder languageHolder;

    @Autowired
    @Qualifier(value = "secureDBLanguageHolder")
    public void setLanguageHolder(LanguageHolder languageHolder) {
        this.languageHolder = languageHolder;
    }

    @Override
    public void afterJdbcTemplateSet() {
        super.afterJdbcTemplateSet();
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(TABLE_NAME)
                .withSchemaName(DataBase.SECURE_DB.getSchema());
        jdbcInsert.setGeneratedKeyName("id");
    }

    /**
     * Створити запит отримання всіх записів
     * @return запит отримання всіх записів
     */
    private static String buildFindAllSql() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT p.*,");
        sql.append(" mv1.value AS name_value, mv1.lang AS name_lang,");
        sql.append(" mv2.value AS descr_value, mv2.lang AS descr_lang");
        sql.append(" FROM permissions p");
        sql.append(" INNER JOIN msg_langs l ON TRUE");
        sql.append(" LEFT JOIN msg_values mv1 ON mv1.const = p.name_const AND mv1.lang = l.code");
        sql.append(" LEFT JOIN msg_values mv2 ON mv2.const = p.description_const AND mv2.lang = l.code");
        return sql.toString();
    }

    @Override
    public Permission mapEntity(SqlRowSet srs) {
        Long id = srs.getLong("id");
        Permission p = new Permission();
        p.setId(id);
        p.setModuleId(srs.getInt("module_id"));
        p.setNameCode(srs.getString("name_const"));
        p.setDescCode(srs.getString("description_const"));
        while (srs.getLong("id") == id) {
            MapperUtil.addResources(srs, languageHolder, p);
            if (!srs.next()) {
                break;
            }
        }
        return p;
    }

    @Override
    public void save(Permission p) {
        Map<String, Object> params = new HashMap<>();
        params.put("name_const", p.getNameCode());
        params.put("description_const", p.getDescCode());
        params.put("module_id", p.getModuleId());
        Long id = jdbcInsert.executeAndReturnKey(params).longValue();
        p.setId(id);
    }

    @Override
    public void update(Permission p) {

    }

    @Override
    public String getFindByIdSql() {
        return FIND_BY_ID;
    }

    @Override
    public String getFindAllSql() {
        return FIND_ALL;
    }

    @Override
    public List<Permission> findByUser(Long userId) {
        SqlRowSet srs = jdbcTemplate.queryForRowSet(FIND_BY_USER, userId);
        return mapAll(srs);
    }

    @Override
    public List<Permission> findByRole(Long roleId) {
        SqlRowSet srs = jdbcTemplate.queryForRowSet(FIND_BY_ROLE, roleId);
        return mapAll(srs);
    }
}

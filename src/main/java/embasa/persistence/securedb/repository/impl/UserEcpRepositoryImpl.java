package embasa.persistence.securedb.repository.impl;

import embasa.enums.DataBase;
import embasa.i18n.LanguageHolder;
import embasa.persistence.securedb.model.Acsk;
import embasa.persistence.securedb.model.User;
import embasa.persistence.securedb.model.UserEcp;
import embasa.persistence.securedb.repository.UserEcpRepository;
import embasa.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Реалізація репозиторія електронного підпису користувача. */
public class UserEcpRepositoryImpl extends SecureDBJdbcRepositoryLocalizableImpl<UserEcp, Long> implements UserEcpRepository {

    /** Ім'я таблиці сутності. */
    private static final String TABLE_NAME = "user_ecp";

    /** Sql-запит вибірки всіх записів. */
    private static final String FIND_ALL = buildFindAll();

    /** Sql-запит вибірки запису за ідентифікатором. */
    private static final String FIND_BY_ID = FIND_ALL + " WHERE ue.id = ?";

    /** Sql-запит вибірки запису за електронним ключем. */
    private static final String FIND_BY_KEY = FIND_ALL + " WHERE ue.credential_value = ? ORDER BY ue.id";

    /** Sql-запит вибірки запису за користувачем. */
    private static final String FIND_BY_USER = FIND_ALL + " WHERE ue.user_id = ? ORDER BY ue.id";

    /** Sql-запит оновлення запису. */
    private static final String UPDATE = String.format(
            "UPDATE %s SET user_id = ?, credential_value = ?, expired_dt = ?, cert_center_id = ?" +
                    " WHERE id = ?", TABLE_NAME);

    /** Утримувач мов. */
    private LanguageHolder languageHolder;

    /** Об'єкт додавання запису за допомогою jdbc. */
    private SimpleJdbcInsert jdbcInsert;

    /** Конструктор за замовчанням. */
    public UserEcpRepositoryImpl() {
        super(TABLE_NAME);
    }

    @Autowired
    @Qualifier(value = "secureDBLanguageHolder")
    public void setLanguageHolder(LanguageHolder languageHolder) {
        this.languageHolder = languageHolder;
    }

    @Override
    public UserEcp findByEdsKey(byte[] key) {
        SqlRowSet srs = jdbcTemplate.queryForRowSet(FIND_BY_KEY, key);
        return mapOne(srs);
    }

    @Override
    public List<UserEcp> findByUser(Long userId) {
        SqlRowSet srs = jdbcTemplate.queryForRowSet(FIND_BY_USER, userId);
        return mapAll(srs);
    }

    @Override
    public void afterJdbcTemplateSet() {
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(TABLE_NAME)
                .withSchemaName(DataBase.SECURE_DB.getSchema());
        jdbcInsert.setGeneratedKeyName("id");
    }

    /**
     * Отримати sql-запит вибірки всіх записів
     * @return sql-запит вибірки всіх записів
     */
    private static String buildFindAll() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT");
        sql.append(" u.id AS user_id, u.login, u.name, u.email, u.account_enabled, u.comment, u.passwd_expiration,");
        sql.append(" cc.id AS acsk_id, cc.cmp_address, cc.cmp_port,");
        sql.append(" mv.const AS name_const, mv.value AS name_value, mv.lang AS name_lang,");
        sql.append(" ue.id, ue.credential_value AS value, ue.expire_dt");
        sql.append(" FROM user_ecp ue");
        sql.append(" LEFT JOIN users u ON u.id = ue.user_id");
        sql.append(" LEFT JOIN cert_centers cc ON cc.id = ue.cert_center_id");
        sql.append(" LEFT JOIN msg_langs ml ON TRUE");
        sql.append(" LEFT JOIN msg_values mv ON mv.const = issuer_cns_const AND mv.lang = ml.code");
        return sql.toString();
    }

    @Override
    public void save(UserEcp p) {
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", p.getUser().getId());
        params.put("credential_value", p.getValue());
        params.put("expire_dt", p.getExpire());
        params.put("cert_center_id", p.getAcsk().getId());
        Long id = jdbcInsert.executeAndReturnKey(params).longValue();
        p.setId(id);
    }

    @Override
    public void update(UserEcp p) {
        jdbcTemplate.update(UPDATE, p.getUser().getId(), p.getValue(), p.getExpire(), p.getAcsk().getId(), p.getId());
    }

    @Override
    public String getFindByIdSql() { return FIND_BY_ID; }

    @Override
    public String getFindAllSql() {
        return FIND_ALL;
    }

    @Override
    public UserEcp mapEntity(SqlRowSet srs) {
        UserEcp userEcp = new UserEcp();
        try {
            User user = new User();
            user.setId(srs.getLong("user_id"));
            user.setUsername(srs.getString("login"));
            user.setEmail(srs.getString("email"));
            user.setEnabled(srs.getBoolean("account_enabled"));
            user.setComment(srs.getString("comment"));
            user.setPasswordExpiration(srs.getDate("passwd_expiration"));
            userEcp.setUser(user);

            userEcp.setId(srs.getLong("id"));
            userEcp.setValue(((ResultSetWrappingSqlRowSet) srs).getResultSet().getBytes("value"));
            userEcp.setExpire(srs.getDate("expire_dt"));
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
        Long acskId = srs.getLong("acsk_id");
        Acsk acsk = MapperUtil.mapAcsk(srs, languageHolder);
        acsk.setId(acskId);
        userEcp.setAcsk(acsk);
        return userEcp;
    }
}

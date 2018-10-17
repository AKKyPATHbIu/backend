package embasa.persistence.common.repository.impl;

import embasa.persistence.BaseJdbcRepositoryImpl;
import embasa.persistence.common.model.MsgValue;
import embasa.persistence.common.repository.MsgValueRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Реалізація репозиторію ресурсів локалізації. */
public class MsgValueRepositoryImpl extends BaseJdbcRepositoryImpl<MsgValue, String> implements MsgValueRepository {

    /** Назва таблиці сутностей. */
    private static String TABLE_NAME = "msg_values";

    /** Об'єкт для додавання записів. */
    private SimpleJdbcInsert jdbcInsert;

    /** Скрипт пошуку всіх записів. */
    private static String FIND_ALL = String.format("SELECT * FROM %s", TABLE_NAME);

    /** Скрипт пошуку запису за ідентифікатором. */
    private static String FIND_BY_ID = FIND_ALL + " WHERE const = ?";

    /** Скрипт видалення запису. */
    private static String DELETE = String.format("DELETE FROM %s WHERE const = ?", TABLE_NAME);

    /** Скрипт перевірки існування запису. */
    private static String IS_EXISTS = String.format("SELECT EXISTS(SELECT const FROM %s WHERE const = ?)", TABLE_NAME);

    /** Скрипт оновлення запису. */
    private static String UPDATE = String.format("UPDATE %s SET value = ? WHERE const = ? AND lang = ?", TABLE_NAME);

    /** Схема бд. */
    private final String schemaName;

    /**
     * Конструктор
     * @param schemaName схема бд
     */
    public MsgValueRepositoryImpl(String schemaName) {
        super(TABLE_NAME);
        this.schemaName = schemaName;
    }

    /**
     * Встановити посилання на шаблон-jdbc
     * @param jdbcTemplate встановлюване значення
     */
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(TABLE_NAME).withSchemaName(schemaName);
    }

    @Override
    public void save(MsgValue p) {
        Map<String, Object> params = new HashMap<>();
        params.put("const", p.getCode());
        params.put("lang", p.getLangCode());
        params.put("value", p.getValue());
        jdbcInsert.execute(params);
    }

    @Override
    public void update(MsgValue p) {
        jdbcTemplate.update(UPDATE, p.getValue(), p.getCode(), p.getLangCode());
    }

    @Override
    public MsgValue mapRow(ResultSet rs, int rowNum) throws SQLException {
        MsgValue result = new MsgValue();
        result.setCode(rs.getString("const"));
        result.setLangCode(rs.getString("lang"));
        result.setValue(rs.getString("value"));
        return result;
    }

    @Override
    public List<MsgValue> findByCode(String code) {
        return jdbcTemplate.query(FIND_BY_ID, new Object[] { code }, new int[] { Types.VARCHAR }, this);
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
    public String getIsExistsSql() {
        return IS_EXISTS;
    }

    @Override
    public String getDeleteSql() {
        return DELETE;
    }
}

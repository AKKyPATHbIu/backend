package embasa.persistence.maindb.repository.impl;

import embasa.enums.DataBase;
import embasa.i18n.LanguageHolder;
import embasa.persistence.maindb.model.Trigger;
import embasa.persistence.maindb.repository.TriggerRepository;
import embasa.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class TriggerRepositoryImpl extends MainDBJdbcRepositoryLocalizableImpl<Trigger, Long>
        implements TriggerRepository, RowMapper<Trigger> {

    /** Утримувач мов. */
    private LanguageHolder languageHolder;

    /** Ім'я таблиці. */
    private static String TABLE_NAME = "triggers";

    /** Збережена процедура видалення сутності. */
    private SimpleJdbcCall jdbcCallDelete;

    /** Додавання запису. */
    private static String ADD = "SELECT trigger_add(%s)";

    /** Скрипт вибірки всіх записів. */
    private static String FIND_ALL = buildFindAllSql();

    /** Скрипт вибірки запису за ідентифікатором.*/
    private static String FIND_BY_ID = FIND_ALL + " WHERE tr.id = ?";

    /** Конструктор за замовчанням. */
    public TriggerRepositoryImpl() {
        super(TABLE_NAME);
    }

    @Override
    public void afterJdbcTemplateSet() {
        super.afterJdbcTemplateSet();
        this.jdbcCallDelete = new SimpleJdbcCall(jdbcTemplate).withFunctionName("trigger_del")
                .withSchemaName(DataBase.MAIN_DB.getSchema());
    }

    @Autowired
    @Qualifier(value = "mainDBLanguageHolder")
    public void setLanguageHolder(LanguageHolder languageHolder) {
        this.languageHolder = languageHolder;
    }

    /**
     * Створити скрипт отримання всіх записів таблиці
     * @return скрипт отримання всіх записів таблиці
     */
    private static String buildFindAllSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT tr.*,");
        sql.append(" mv1.value AS name_value, mv1.lang AS name_lang,");
        sql.append(" mv2.value AS descr_value, mv2.lang AS descr_lang");
        sql.append(" FROM triggers tr");
        sql.append(" INNER JOIN msg_langs l ON TRUE");
        sql.append(" LEFT JOIN msg_values mv1 ON mv1.const = tr.name_const AND mv1.lang = l.code");
        sql.append(" LEFT JOIN msg_values mv2 ON mv2.const = tr.descr_const AND mv2.lang = l.code");
        return sql.toString();
    }

    @Override
    public void save(Trigger p) {
        String entityParam = "'" + p.listFieldsValues() + "'";
        Long id = jdbcTemplate.queryForObject(String.format(ADD, entityParam), Long.class);
        p.setId(id);
    }

    @Override
    public void delete(Long id) {
        SqlParameterSource inId = new MapSqlParameterSource().addValue("p_trigger_id", id);
        jdbcCallDelete.execute(inId);
    }

    @Override
    public Trigger mapEntity(SqlRowSet srs) {
        return MapperUtil.mapTrigger(srs, languageHolder);
    }

    @Override
    public String getFindByIdSql() {
        return FIND_BY_ID;
    }

    @Override
    public String getFindAllSql() {
        return FIND_ALL;
    }
}

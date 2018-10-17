package embasa.persistence.maindb.repository.impl;

import embasa.enums.DataBase;
import embasa.i18n.LanguageHolder;
import embasa.persistence.maindb.model.Validator;
import embasa.persistence.maindb.repository.ValidatorRepository;
import embasa.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.sql.ResultSet;
import java.sql.SQLException;

/** Реалізація репозиторію валідатора. */
public class ValidatorRepositoryImpl extends MainDBJdbcRepositoryLocalizableImpl<Validator, Long>
        implements ValidatorRepository {

    /** Назва таблиці сутностей. */
    private static String tablename = "validators";

    /** Додавання запису. */
    private static String ADD = "SELECT validator_add(%s)";

    /** Запит вибірки всіх валідаторів. */
    private static String FIND_ALL = buildFindAllSql();

    /** Запит вибырки валідатора за ідентифікатором. */
    private static String FIND_BY_ID = FIND_ALL + " WHERE v.id =?";

    /** Збережена процедура видалення сутності. */
    private SimpleJdbcCall jdbcCallDelete;

    /** Утримувач мов. */
    private LanguageHolder languageHolder;

    @Autowired
    @Qualifier(value = "mainDBLanguageHolder")
    public void setLanguageHolder(LanguageHolder languageHolder) {
        this.languageHolder = languageHolder;
    }

    /**
     * Отримати запит вибірки всіх валідаторів
     * @return запит вибірки всіх валідаторів
     */
    private static String buildFindAllSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT v.*, mv1.value AS name_value, mv1.lang AS name_lang,");
        sql.append(" mv2.value AS descr_value, mv2.lang AS descr_lang");
        sql.append(String.format(" FROM %s v", tablename));
        sql.append(" INNER JOIN msg_langs ml ON TRUE");
        sql.append(" LEFT JOIN msg_values mv1 ON mv1.lang = ml.code AND mv1.const = v.name_const");
        sql.append(" LEFT JOIN msg_values mv2 ON mv2.lang = ml.code AND mv2.const = v.descr_const");
        return sql.toString();
    }

    /** Конструктор за замовчанням. */
    public ValidatorRepositoryImpl() {
        super(tablename);
    }

    @Override
    public void afterJdbcTemplateSet() {
        super.afterJdbcTemplateSet();
        jdbcCallDelete = new SimpleJdbcCall(jdbcTemplate).withFunctionName("validator_del")
                .withSchemaName(DataBase.MAIN_DB.getSchema());
    }

    @Override
    public void save(Validator p) {
        String validator = "'" + p.listFieldsValues() + "'";
        Long id = jdbcTemplate.queryForObject(String.format(ADD, validator), Long.class);
        p.setId(id);
    }

    @Override
    public void delete(Long id) {
        SqlParameterSource inId = new MapSqlParameterSource().addValue("p_validator_id", id);
        jdbcCallDelete.execute(inId);
    }

    @Override
    public Validator mapEntity(SqlRowSet srs) {
        return MapperUtil.mapValidator(srs, languageHolder);
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

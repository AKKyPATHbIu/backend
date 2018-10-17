package embasa.persistence.maindb.repository.impl;

import embasa.enums.DataBase;
import embasa.i18n.LanguageHolder;
import embasa.persistence.maindb.model.Module;
import embasa.persistence.maindb.repository.ModuleRepository;
import embasa.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.HashMap;
import java.util.Map;

/** Реалізація репозиторія модулей системи. */
public class ModuleRepositoryImpl extends MainDBJdbcRepositoryLocalizableImpl<Module, Long> implements ModuleRepository {

    /** Ім'я таблиці сутностей. */
    private static String TABLE_NAME = "modules";

    /** Скрипт отримання всіх записів. */
    private static String FIND_ALL = buildFindAllSql();

    /** Скрипт отримання запису за ідентифікатором. */
    private static String FIND_BY_ID = FIND_ALL + " WHERE m.id = ?";

    /** Додавання сутності. */
    private SimpleJdbcInsert jdbcInsert;

    /** Утримувач мов. */
    private LanguageHolder languageHolder;

    /** Конструктор за замовчанням. */
    public ModuleRepositoryImpl() {
        super(TABLE_NAME);
    }

    @Override
    public void afterJdbcTemplateSet() {
        super.afterJdbcTemplateSet();
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(TABLE_NAME)
                .withSchemaName(DataBase.MAIN_DB.getSchema());
        jdbcInsert.setGeneratedKeyName("id");
    }

    @Autowired
    @Qualifier(value = "mainDBLanguageHolder")
    public void setLanguageHolder(LanguageHolder languageHolder) {
        this.languageHolder = languageHolder;
    }

    /**
     * Створити скрипт отримання всіх записів
     * @return скрипт отримання всіх записів
     */
    private static String buildFindAllSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT m.*,");
        sql.append(" mv1.value AS name_value, mv1.lang AS name_lang,");
        sql.append(" mv2.value AS descr_value, mv2.lang AS descr_lang");
        sql.append(String.format(" FROM %s m", TABLE_NAME));
        sql.append(" INNER JOIN msg_langs l ON TRUE");
        sql.append(" LEFT JOIN msg_values mv1 ON mv1.const = m.name_const AND mv1.lang = l.code");
        sql.append(" LEFT JOIN msg_values mv2 ON mv2.const = m.descr_const AND mv2.lang = l.code");
        return sql.toString();
    }

    @Override
    public Module mapEntity(SqlRowSet srs) {
        Module module = new Module();
        Long id = srs.getLong("id");
        String nameCode = srs.getString("name_const");
        String descrCode = srs.getString("descr_const");
        module.setId(id);
        module.getName().setCode(nameCode);
        module.getDescr().setCode(descrCode);

        while(srs.getLong("id") == id) {
            MapperUtil.addResources(srs, languageHolder, module);
            if (!srs.next()) {
                break;
            }
        }
        return module;
    }

    @Override
    public void save(Module p) {
        Map<String, Object> params = new HashMap<>();
        params.put("name_const", p.getName().getCode());
        params.put("descr_const", p.getDescr().getCode());
        Long id = jdbcInsert.executeAndReturnKey(params).longValue();
        p.setId(id);
    }

    @Override
    public void update(Module p) {

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

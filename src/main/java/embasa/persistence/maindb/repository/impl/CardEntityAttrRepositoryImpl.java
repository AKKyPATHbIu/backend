package embasa.persistence.maindb.repository.impl;

import embasa.enums.DataBase;
import embasa.i18n.LanguageHolder;
import embasa.persistence.maindb.model.CardEntityAttr;
import embasa.persistence.maindb.model.CardEntityValidator;
import embasa.persistence.maindb.model.Validator;
import embasa.persistence.maindb.repository.CardEntityAttrRepository;
import embasa.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Реалізація репозиторія атрибутів сутності конструктора. */
public class CardEntityAttrRepositoryImpl extends MainDBJdbcRepositoryLocalizableImpl<CardEntityAttr, Long>
        implements CardEntityAttrRepository {

    /** Утримувач мов. */
    private LanguageHolder languageHolder;

    /** Об'єкт для додавання записів. */
    private SimpleJdbcInsert jdbcInsert;

    /** Імя таблиці сутності. */
    private static String TABLE_NAME = "entity_attr";

    /** Скрипт вибірки всіх записів. */
    private static String FIND_ALL = buildFindAllSql();

    /** Скрипт вибірки запису за ідентифікатором.*/
    private static String FIND_BY_ID = FIND_ALL + " WHERE attr.id = ?";

    /** Скрипт пошуку повязаних атрибутів сутності картки конструктора. */
    private static String FIND_BY_CARD_ENTITY = FIND_ALL + " WHERE attr.entity_id = ?";

    /**
     * Створити скрипт отримання всіх записів таблиці
     * @return скрипт отримання всіх записів таблиці
     */
    private static String buildFindAllSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT attr.*, vattr.params, v.id AS validator_id, v.data_type_id, v.rule,");
        sql.append(" mv1.const AS name_const, mv1.value AS name_value, mv1.lang AS name_lang,");
        sql.append(" mv2.const AS descr_const, mv2.value AS descr_value, mv2.lang AS descr_lang");
        sql.append(" FROM entity_attr attr");
        sql.append(" LEFT JOIN entity_attr_validators vattr ON vattr.attr_id = attr.id");
        sql.append(" LEFT JOIN validators v ON v.id = vattr.validator_id");
        sql.append(" LEFT JOIN msg_langs l ON v.id IS NOT NULL");
        sql.append(" LEFT JOIN msg_values mv1 ON mv1.const = v.name_const AND mv1.lang = l.code");
        sql.append(" LEFT JOIN msg_values mv2 ON mv2.const = v.descr_const AND mv2.lang = l.code");
        return sql.toString();
    }

    /** Конструктор за замовчанням. */
    public CardEntityAttrRepositoryImpl() {
        super(TABLE_NAME);
    }

    @Override
    public void afterJdbcTemplateSet() {
        super.afterJdbcTemplateSet();
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource()).withTableName(tablename)
                .withSchemaName(DataBase.MAIN_DB.getSchema());
        jdbcInsert.setGeneratedKeyName("id");
    }

    @Autowired
    @Qualifier(value = "mainDBLanguageHolder")
    public void setLanguageHolder(LanguageHolder languageHolder) {
        this.languageHolder = languageHolder;
    }

    @Override
    public List<CardEntityAttr> getByCardEntity(Long entityId) {
        return jdbcTemplate.query(FIND_BY_CARD_ENTITY,
                new Object[] { entityId }, new int[] {Types.BIGINT} , this);
    }

    @Override
    public CardEntityAttr mapRow(ResultSet rs, int rowNum) throws SQLException {
        CardEntityAttr result = new CardEntityAttr();
        result.setId(rs.getLong("id"));
        result.setConstrEntityId(rs.getLong("entity_id"));
        result.setName(rs.getString("attr_name")).setType(rs.getLong("attr_type"));
        result.setDescr(rs.getString("attr_desc")).setAudited(rs.getBoolean("is_audited"));
        result.setPosition(rs.getLong("position")).setRefConstrEntityId(rs.getLong("ref_entity_id"));
        result.setUiConf(rs.getString("ui_conf")).setFieldName(rs.getString("field_name"));
        result.setNullable(rs.getBoolean("is_nullable")).setIndexed(rs.getBoolean("indexed"));
        result.setStatus(rs.getLong("status"));
        return result;
    }

    @Override
    public void save(CardEntityAttr p) {
        Map<String, Object> params = new HashMap<>();
        params.put("entity_id", p.getConstrEntityId());
        params.put("attr_name", p.getName());
        params.put("attr_type", p.getType());
        params.put("attr_desc", p.getDescr());
        params.put("is_audited", p.getAudited());
        params.put("position", p.getPosition());
        params.put("ref_entity_id", p.getRefConstrEntityId());
        params.put("ui_conf", p.getUiConf());
        params.put("field_name", p.getFieldName());
        params.put("is_nullable", p.getNullable());
        params.put("indexed", p.getIndexed());
        params.put("status", p.getStatus());
        Long id = jdbcInsert.executeAndReturnKey(params).longValue();
        p.setId(id);
    }

    @Override
    public void update(CardEntityAttr p) {

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
    public CardEntityAttr mapEntity(SqlRowSet srs) {

        CardEntityAttr entityAttr = new CardEntityAttr();
        entityAttr.setId(srs.getLong("id"));
        entityAttr.setConstrEntityId(srs.getLong("entity_id"));
        entityAttr.setName(srs.getString("attr_name")).setType(srs.getLong("attr_type"));
        entityAttr.setDescr(srs.getString("attr_desc")).setAudited(srs.getBoolean("is_audited"));
        entityAttr.setPosition(srs.getLong("position")).setRefConstrEntityId(srs.getLong("ref_entity_id"));
        entityAttr.setUiConf(srs.getString("ui_conf")).setFieldName(srs.getString("field_name"));
        entityAttr.setNullable(srs.getBoolean("is_nullable")).setIndexed(srs.getBoolean("indexed"));
        entityAttr.setStatus(srs.getLong("status"));

        while (!srs.isAfterLast() && srs.getLong("id") == entityAttr.getId()) {
            Long validatorId = srs.getLong("validator_id");
            if (validatorId != null) {
                Long dataTypeId = srs.getLong("data_type_id");
                String nameConst = srs.getString("name_const");
                String descrConst = srs.getString("descr_const");
                String rule = srs.getString("rule");

                Validator validator = new Validator();
                validator.setId(validatorId);
                validator.setDataTypeId(dataTypeId);
                validator.setNameCode(nameConst);
                validator.setDescCode(descrConst);
                validator.setRule(rule);

                CardEntityValidator entityValidator = new CardEntityValidator();
                entityValidator.setValidator(validator);
                entityValidator.setParams(srs.getString("params"));
                entityAttr.getValidators().add(entityValidator);

                while (srs.getLong("validator_id") == validatorId) {
                    MapperUtil.addResources(srs, languageHolder, validator);
                    if (!srs.next() || srs.getLong("id") != entityAttr.getId()) {
                        break;
                    }
                }
            }
        }

        return entityAttr;
    }
}

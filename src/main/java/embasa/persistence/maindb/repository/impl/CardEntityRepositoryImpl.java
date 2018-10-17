package embasa.persistence.maindb.repository.impl;

import embasa.enums.DataBase;
import embasa.persistence.maindb.model.CardEntity;
import embasa.persistence.maindb.repository.CardEntityRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import java.sql.ResultSet;
import java.sql.SQLException;

/** Репозиторій сутностей системи. */
public class CardEntityRepositoryImpl extends MainDBJdbcRepositoryImpl<CardEntity, Long>
        implements CardEntityRepository, RowMapper<CardEntity> {

    /** Додавання запису. */
    private static String ADD = "SELECT entity_add(%s, %s)";

    /** Збережена процедура видалення сутності. */
    private SimpleJdbcCall jdbcCallDelete;

    /** Перелік полів для відбору з бази. */
    private final String FIELD_LIST = "*, CAST(ui_conf AS text) AS ui_conf";

    /** Конструктор за замовчанням. */
    public CardEntityRepositoryImpl() {
        super("entities");
    }

    @Override
    public void afterJdbcTemplateSet() {
        super.afterJdbcTemplateSet();
        this.jdbcCallDelete = new SimpleJdbcCall(jdbcTemplate).withFunctionName("entity_del")
                .withSchemaName(DataBase.MAIN_DB.getSchema());
    }

    @Override
    public CardEntity mapRow(ResultSet resultSet, int i) throws SQLException {
        CardEntity cardEntity = new CardEntity();
        cardEntity.setId(resultSet.getLong("id"));
        cardEntity.setClinicId(resultSet.getLong("clinica_id"))
                .setModuleId(resultSet.getLong("module_id")).setSystem(resultSet.getBoolean("system"))
                .setName(resultSet.getString("name")).setDescr(resultSet.getString("descr"))
                .setStatus(resultSet.getLong("status")).setTableName(resultSet.getString("table_name"))
                .setViewName(resultSet.getString("view_name")).setUiConf(resultSet.getString("ui_conf"));
        return cardEntity;
    }

    @Override
    public void save(CardEntity p) {
        String entityParam = "'" + p.listFieldsValues() + "'";
        String attrParam = p.getAttr().listFieldsValues() + "::entity_attr[]";
        Long id = jdbcTemplate.queryForObject(String.format(ADD, entityParam, attrParam), Long.class);
        p.setId(id);
    }

    @Override
    public void delete(Long id) {
        SqlParameterSource inId = new MapSqlParameterSource().addValue("p_ent_id", id);
        jdbcCallDelete.execute(inId);
    }

    @Override
    public String getFindByIdSql() {
        return super.getFindByIdSql().replace("*", FIELD_LIST);
    }

    @Override
    public String getFindAllSql() {
        return super.getFindAllSql().replace("*", FIELD_LIST);
    }
}

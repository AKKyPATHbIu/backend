package embasa.persistence.maindb.repository.impl;

import embasa.enums.DataBase;
import embasa.persistence.maindb.model.WfTransition;
import embasa.persistence.maindb.model.WfTransitionTrigger;
import embasa.persistence.maindb.model.WfTransitionValidator;
import embasa.persistence.maindb.repository.WfTransitionRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Реалізація інтерфейса репозиторія переходів статуса workflow.
 * Поля triggers і validators сутності {@link embasa.persistence.maindb.model.WfTransition} не заповнюються
 * при завантаженні, необхідно вручну заповнювати за допомогою репозиторієв
 * {@link embasa.persistence.maindb.repository.WfTransitionTriggerRepository} та
 * {@link embasa.persistence.maindb.repository.WfTransitionValidatorRepository} відповідно
 */
public class WfTransitionRepositoryImpl extends MainDBJdbcRepositoryImpl<WfTransition, Long>
        implements WfTransitionRepository, RowMapper<WfTransition> {

    /** Ім'я таблиці сутності. */
    private static String TABLE_NAME = "wf_transitions";

    /** Ім'я функції видалення сутності. */
    private static String FUNC_DELETE = "wf_trans_del";

    /** Скрипт додавання запису. */
    private static String ADD = "SELECT wf_trans_add(%s)";

    /** Скрипт оновлення запису. */
    private static String UPDATE = "SELECT wf_trans_edit(%s)";

    /** Збережена процедура видалення сутності. */
    private SimpleJdbcCall jdbcCallDelete;

    /** Конструктор за замовчанням. */
    public WfTransitionRepositoryImpl() {
        super(TABLE_NAME);
    }

    @Override
    public void afterJdbcTemplateSet() {
        super.afterJdbcTemplateSet();
        jdbcCallDelete = new SimpleJdbcCall(jdbcTemplate).withFunctionName(FUNC_DELETE)
                .withSchemaName(DataBase.MAIN_DB.getSchema());
    }

    @Override
    public void save(WfTransition p) {
        Long id = jdbcTemplate.queryForObject(String.format(ADD, p.listFieldsValues()), Long.class);
        p.setId(id);
        for (WfTransitionTrigger trTrigger : p.getTriggers()) {
            trTrigger.setTransitionId(id);
        }
        for (WfTransitionValidator trValidator : p.getValidators()) {
            trValidator.setTransitionId(id);
        }
    }

    @Override
    public void update(WfTransition p) {
        String params = p.getId() + "," + p.listFieldsValues();
        jdbcTemplate.execute(String.format(UPDATE, params));
    }

    @Override
    public void delete(Long id) {
        SqlParameterSource inId = new MapSqlParameterSource().addValue("p_id", id);
        jdbcCallDelete.execute(inId);
    }

    @Override
    public WfTransition mapRow(ResultSet rs, int rowNum) throws SQLException {
        WfTransition result = new WfTransition();
        result.setId(rs.getLong("id"));
        result.setEntityId(rs.getLong("entity_id"));
        result.setStatusId(rs.getLong("status_id"));
        result.setNextStatusId(rs.getLong("next_status_id"));
        result.setLevel(rs.getInt("level"));
        return result;
    }
}

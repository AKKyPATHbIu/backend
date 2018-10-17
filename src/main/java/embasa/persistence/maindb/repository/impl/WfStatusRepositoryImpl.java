package embasa.persistence.maindb.repository.impl;

import embasa.enums.DataBase;
import embasa.persistence.maindb.model.WfStatus;
import embasa.persistence.maindb.repository.WfStatusRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/** Реалізація репозиторія статуса workflow. */
public class WfStatusRepositoryImpl extends MainDBJdbcRepositoryImpl<WfStatus, Long>
        implements WfStatusRepository, RowMapper<WfStatus> {

    /** Ім'я таблиці.*/
    private static String TABLE_NAME = "wf_statuses";

    /** Ім'я функції видалення запису. */
    private static String FUNC_DELETE = "wf_status_del";

    /** Запит додавання запису. */
    private static String INSERT = "SELECT wf_status_add(?,?,?)";

    /** Запит редагування запису. */
    private static String UPDATE = "SELECT wf_status_edit(?,?,?,?)";

    /** Збережена процедура видалення сутності. */
    private SimpleJdbcCall jdbcCallDelete;

    /** Конструктор за замовчанням. */
    public WfStatusRepositoryImpl() {
        super(TABLE_NAME);
    }

    @Override
    public void afterJdbcTemplateSet() {
        super.afterJdbcTemplateSet();
        jdbcCallDelete = new SimpleJdbcCall(jdbcTemplate).withFunctionName(FUNC_DELETE)
                .withSchemaName(DataBase.MAIN_DB.getSchema());
    }

    @Override
    public void save(WfStatus p) {
        Long id = jdbcTemplate.queryForObject(INSERT, new Object[] { p.getClinicId(), p.getName(), p.getDescr() },
                new int[] { Types.BIGINT, Types.VARCHAR, Types.VARCHAR }, Long.class);
        p.setId(id);
    }

    @Override
    public void update(WfStatus p) {
        jdbcTemplate.queryForObject(UPDATE, new Object[] { p.getId(), p.getClinicId(), p.getName(), p.getDescr() },
                new int[] { Types.BIGINT, Types.BIGINT, Types.VARCHAR, Types.VARCHAR }, Object.class);
    }

    @Override
    public void delete(Long id) {
        SqlParameterSource inId = new MapSqlParameterSource().addValue("p_wf_status_id", id);
        jdbcCallDelete.execute(inId);
    }

    @Override
    public WfStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
        WfStatus result = new WfStatus();
        result.setId(rs.getLong("id"));
        result.setClinicId(rs.getLong("clinic_id"));
        result.setName(rs.getString("name")).setDescr(rs.getString("descr"));
        return result;
    }
}

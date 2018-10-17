package embasa.persistence.maindb.repository.impl;

import embasa.enums.DataBase;
import embasa.persistence.maindb.model.Clinic;
import embasa.persistence.maindb.repository.ClinicRepository;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/** Реалізація репозиторія медичних закладів. */
public class ClinicRepositoryImpl extends MainDBJdbcRepositoryInsertImpl<Clinic, Long> implements ClinicRepository {

    /** Ім'я таблиці. */
    private static final String TABLE_NAME = "clinics";

    /** Sql-скрипт оновлення запису. */
    private static final String UPDATE = String.format(
            "UPDATE %s SET descr = ?, name = ?, status = ? WHERE id = ?", TABLE_NAME);

    /** Конструктор за замовчанням. */
    public ClinicRepositoryImpl() {
        super(TABLE_NAME);
    }

    @Override
    public void save(Clinic p) {
        Map<String, Object> params = new HashMap<> ();
        params.put("descr", p.getDescription());
        params.put("name", p.getName());
        params.put("status", p.getStatus());
        Long id = jdbcInsert.executeAndReturnKey(params).longValue();
        p.setId(id);
    }

    @Override
    public void update(Clinic p) {
        jdbcTemplate.update(UPDATE, new Object[] { p.getDescription(), p.getName(), p.getStatus(), p.getId() },
                new int[] { Types.VARCHAR, Types.VARCHAR, Types.BIGINT, Types.BIGINT }, this);
    }

    @Override
    public Clinic mapRow(ResultSet resultSet, int i) throws SQLException {
        Clinic c = new Clinic();
        c.setId(resultSet.getLong("id"));
        c.setName(resultSet.getString("name"));
        c.setDescription(resultSet.getString("descr"));
        c.setStatus(resultSet.getLong("status"));
        return c;
    }
}

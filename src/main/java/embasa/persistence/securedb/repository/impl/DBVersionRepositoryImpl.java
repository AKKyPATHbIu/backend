package embasa.persistence.securedb.repository.impl;

import embasa.persistence.BaseJdbcRepositoryImpl;
import embasa.persistence.securedb.model.DBVersion;
import embasa.persistence.securedb.repository.DBVersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;

/** Реалізація репозиторія версій бд. */
public class DBVersionRepositoryImpl extends BaseJdbcRepositoryImpl<DBVersion, String> implements DBVersionRepository {

    /** Ім'я таблиці сутностей. */
    private static final String TABLE_NAME = "sys_versions";

    /** Запит пошуку останьої за датою активації версії бд. */
    private static final String FIND_LAST = String.format("SELECT * FROM %s ORDER BY apply_dt DESC LIMIT 1", TABLE_NAME);

    /** Конструктор за замовчанням. */
    public DBVersionRepositoryImpl() {
        super(TABLE_NAME);
        this.pkName = "version";
    }

    @Autowired
    @Qualifier("secureDBJdbcTemplate")
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        super.setJdbcTemplate(jdbcTemplate);
    }

    @Override
    public void save(DBVersion p) { }

    @Override
    public void update(DBVersion p) { }

    @Override
    public void delete(String id) { }

    @Override
    public DBVersion mapRow(ResultSet resultSet, int i) throws SQLException {
        DBVersion version = new DBVersion();
        version.setVersion(resultSet.getString("version"));
        version.setDescription(resultSet.getString("desc"));
        version.setApplyDate(resultSet.getDate("apply_dt"));
        return version;
    }

    @Override
    public DBVersion findByVersion(String version) {
        return findById(version);
    }

    @Override
    public DBVersion findLastByApplyDate() {
        return jdbcTemplate.queryForObject(FIND_LAST, this);
    }
}

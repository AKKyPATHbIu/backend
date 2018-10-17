package embasa.persistence;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Базовий клас репозиторію для сутностей, що потребують локалізацію. */
public abstract class BaseJdbcRepositoryLocalizableImpl<T, PK extends Serializable> extends BaseJdbcRepositoryImpl<T, PK>
        implements BaseJdbcRepository<T, PK> {

    /**
     * Конструктор
     * @param tablename ім'я таблиці сутності
     */
    public BaseJdbcRepositoryLocalizableImpl(String tablename) {
        super(tablename);
    }

    @Override
    public T findById(PK id) {
        SqlRowSet srs = jdbcTemplate.queryForRowSet(getFindByIdSql(), id);
        return mapOne(srs);
    }

    /**
     * Перетворити набір даних на сутність
     * @param srs набір даних
     * @return сутність з набору даних
     */
    public T mapOne(SqlRowSet srs) {
        return !srs.isAfterLast() && srs.next() ? mapEntity(srs) : null;
    }

    @Override
    public List<T> findAll() {
        SqlRowSet srs = jdbcTemplate.queryForRowSet(getFindAllSql());
        return mapAll(srs);
    }

    /**
     * Перетворити набір даних на сутності
     * @param srs набір даних
     * @return сутності
     */
    public List<T> mapAll(SqlRowSet srs) {
        List<T> result = new ArrayList<>();
        if (!srs.isAfterLast() && srs.next()) {
            while (!srs.isAfterLast()) {
                result.add(mapEntity(srs));
            }
        }
        return result;
    }

    /**
     * Створити об'єкт сутності з набору даних
     * @param srs набір даних
     * @return об'єкт сутності з набору даних
     */
    public abstract T mapEntity(SqlRowSet srs);

    @Override
    public T mapRow(ResultSet rs, int rowNum) throws SQLException {
        return null;
    }
}

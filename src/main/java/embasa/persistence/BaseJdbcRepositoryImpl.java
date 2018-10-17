package embasa.persistence;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.util.List;

/** Реалізація базового репозиторія. */
public abstract class BaseJdbcRepositoryImpl<T, PK extends Serializable> implements BaseJdbcRepository<T, PK>, RowMapper<T> {

    /** Шаблон для доступу до бд. */
    protected JdbcTemplate jdbcTemplate;

    /** Ім'я таблиці сутності. */
    protected String tablename;

    /** Ім'я первиного ключа. */
    protected String pkName = "id";

    /** Запит отримання запису за ідентифікатором. */
    private static String FIND_BY_ID = "SELECT * FROM %s WHERE %s = ?";

    /** Запит отримання всіх записів. */
    private static String FIND_ALL = "SELECT * FROM %s";

    /** Запит-перевірка існування запису з ідентифікатором. */
    private static String IS_EXISTS = "SELECT EXISTS(SELECT id FROM %s WHERE %s = ?)";

    /** Запит-видалення запису сутності за ідентифікатором. */
    private static String DELETE = "DELETE FROM %s WHERE %s = ?";

    /**
     * Конструктор
     * @param tablename ім'я таблиці сутності
     */
    public BaseJdbcRepositoryImpl(String tablename) {
        this.tablename = tablename;
    }

    /**
     * Встановити посилання на шаблон jdbc
     * @param jdbcTemplate встановлюване значення
     */
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        afterJdbcTemplateSet();
    }

    /** Метод, який викликається після встановлення jdbcTemplate. */
    public void afterJdbcTemplateSet() {}

    @Override
    public T findById(PK id) {
        T result = null;
        try {
            result = jdbcTemplate.queryForObject(getFindByIdSql(), new Object[] { id }, this);
        } catch (EmptyResultDataAccessException ex) {

        }
        return result;
    }

    @Override
    public List<T> findAll() {
        return jdbcTemplate.query(getFindAllSql(), this);
    }

    @Override
    public void update(T p) {
        save(p);
    }

    @Override
    public boolean isExist(PK id) {
        return jdbcTemplate.queryForObject(getIsExistsSql(), new Object[] { id }, Boolean.class);
    }

    @Override
    public void delete(PK id) {
        jdbcTemplate.update(getDeleteSql(), id);
    }

    /**
     * Отримати запит пошуку сутності за ідентифікатором
     * @return запит пошуку сутності за ідентифікатором
     */
    public String getFindByIdSql() {
        return String.format(FIND_BY_ID, tablename, pkName);
    }

    /**
     * Отримати запит всіх сутностей
     * @return запит всіх сутностей
     */
    public String getFindAllSql() {
        return String.format(FIND_ALL, tablename);
    }

    /**
     * Отримати запит перевірки існування сутності
     * @return запит перевірки існування сутності
     */
    public String getIsExistsSql() {
        return String.format(IS_EXISTS, tablename, pkName);
    }

    /**
     * Отримати запит видалення запису
     * @return запит видалення запису
     */
    public String getDeleteSql() {
        return String.format(DELETE, tablename, pkName);
    }
}

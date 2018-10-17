package embasa.persistence.maindb.repository.impl;

import embasa.enums.DataBase;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.io.Serializable;

/** Репозиторій з об'єктом для вставки запису. */
public abstract class MainDBJdbcRepositoryInsertImpl<T, PK extends Serializable> extends MainDBJdbcRepositoryImpl<T , PK> {

    /** Об'єкт для додавання запису в таблицю. */
    protected SimpleJdbcInsert jdbcInsert;

    /**
     * Конструктор
     * @param tablename ім'я таблиці сутності
     */
    public MainDBJdbcRepositoryInsertImpl(String tablename) {
        super(tablename);
    }

    @Override
    public void afterJdbcTemplateSet() {
        super.afterJdbcTemplateSet();
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(tablename)
                .withSchemaName(DataBase.MAIN_DB.getSchema());
        jdbcInsert.setGeneratedKeyName(pkName);
    }
}

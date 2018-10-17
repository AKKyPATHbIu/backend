package embasa.persistence.securedb.repository.impl;

import embasa.enums.DataBase;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.io.Serializable;

/** Репозиторій з об'єктом для вставки запису. */
public abstract class SecureDBJdbcRepositoryInsertImpl<T, PK extends Serializable> extends SecureDBJdbcRepositoryImpl<T , PK> {

    /** Об'єкт для додавання запису в таблицю. */
    protected SimpleJdbcInsert jdbcInsert;

    /**
     * Конструктор
     * @param tablename ім'я таблиці сутності
     */
    public SecureDBJdbcRepositoryInsertImpl(String tablename) {
        super(tablename);
    }

    @Override
    public void afterJdbcTemplateSet() {
        super.afterJdbcTemplateSet();
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(tablename)
                .withSchemaName(DataBase.SECURE_DB.getSchema());
        jdbcInsert.setGeneratedKeyName(pkName);
    }
}

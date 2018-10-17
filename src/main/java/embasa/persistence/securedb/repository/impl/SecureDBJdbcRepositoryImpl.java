package embasa.persistence.securedb.repository.impl;

import embasa.persistence.BaseJdbcRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.Serializable;

/** Базова реалізація репозиторія бази {@link embasa.enums.DataBase#SECURE_DB} */
public abstract class SecureDBJdbcRepositoryImpl<T, PK extends Serializable> extends BaseJdbcRepositoryImpl<T, PK> {

    /**
     * Конструктор
     * @param tablename ім'я таблиці сутності
     */
    public SecureDBJdbcRepositoryImpl(String tablename) {
        super(tablename);
    }

    @Autowired
    @Qualifier(value = "secureDBJdbcTemplate")
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        super.setJdbcTemplate(jdbcTemplate);
    }
}

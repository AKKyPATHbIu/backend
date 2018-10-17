package embasa.persistence.securedb.repository.impl;

import embasa.persistence.BaseJdbcRepositoryLocalizableImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.Serializable;

/** Базова реалізація репозиторія локалізованих сутностей бази {@link embasa.enums.DataBase#SECURE_DB} */
public abstract class SecureDBJdbcRepositoryLocalizableImpl<T, PK extends Serializable> extends BaseJdbcRepositoryLocalizableImpl<T, PK> {

    /**
     * Конструктор
     * @param tablename ім'я таблиці сутності
     */
    public SecureDBJdbcRepositoryLocalizableImpl(String tablename) {
        super(tablename);
    }

    @Autowired
    @Qualifier(value = "secureDBJdbcTemplate")
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        super.setJdbcTemplate(jdbcTemplate);
    }
}

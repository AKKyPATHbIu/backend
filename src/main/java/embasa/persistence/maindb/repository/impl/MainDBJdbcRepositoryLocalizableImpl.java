package embasa.persistence.maindb.repository.impl;

import embasa.persistence.BaseJdbcRepositoryLocalizableImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.Serializable;

/** Базова реалізація репозиторія локалізованих сутностей бази {@link embasa.enums.DataBase#MAIN_DB} */
public abstract class MainDBJdbcRepositoryLocalizableImpl<T, PK extends Serializable> extends BaseJdbcRepositoryLocalizableImpl<T, PK> {

    /**
     * Конструктор
     * @param tablename ім'я таблиці сутності
     */
    public MainDBJdbcRepositoryLocalizableImpl(String tablename) {
        super(tablename);
    }

    @Autowired
    @Qualifier(value = "mainDBJdbcTemplate")
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        super.setJdbcTemplate(jdbcTemplate);
    }
}

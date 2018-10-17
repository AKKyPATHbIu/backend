package embasa.persistence.maindb.repository.impl;

import embasa.persistence.BaseJdbcRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.Serializable;

/** Базова реалізація репозиторія бази {@link embasa.enums.DataBase#MAIN_DB} */
public abstract class MainDBJdbcRepositoryImpl<T, PK extends Serializable> extends BaseJdbcRepositoryImpl<T, PK> {

    /**
     * Конструктор
     * @param tablename ім'я таблиці сутності
     */
    public MainDBJdbcRepositoryImpl(String tablename) {
        super(tablename);
    }

    @Autowired
    @Qualifier(value = "mainDBJdbcTemplate")
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        super.setJdbcTemplate(jdbcTemplate);
    }
}

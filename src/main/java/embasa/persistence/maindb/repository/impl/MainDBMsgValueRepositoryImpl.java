package embasa.persistence.maindb.repository.impl;

import embasa.enums.DataBase;
import embasa.persistence.common.repository.impl.MsgValueRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

/** Реалізація репозиторія ресурсів локалізації. */
public class MainDBMsgValueRepositoryImpl extends MsgValueRepositoryImpl {

    /** Конструктор за замовчанням. */
    public MainDBMsgValueRepositoryImpl() {
        super(DataBase.MAIN_DB.getSchema());
    }

    @Autowired
    @Qualifier(value = "mainDBJdbcTemplate")
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        super.setJdbcTemplate(jdbcTemplate);
    }
}

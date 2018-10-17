package embasa.persistence.securedb.repository.impl;

import embasa.enums.DataBase;
import embasa.persistence.common.repository.impl.MsgValueRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

/** Реалізація репозиторія ресурсів локалізації. */
public class SecureDBMsgValueRepositoryImpl extends MsgValueRepositoryImpl {

    /** Конструктор за замовчанням. */
    public SecureDBMsgValueRepositoryImpl() {
        super(DataBase.SECURE_DB.getSchema());
    }

    @Autowired
    @Qualifier(value = "secureDBJdbcTemplate")
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        super.setJdbcTemplate(jdbcTemplate);
    }
}

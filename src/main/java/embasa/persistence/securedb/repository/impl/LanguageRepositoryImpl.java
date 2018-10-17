package embasa.persistence.securedb.repository.impl;

import embasa.persistence.common.model.Language;
import embasa.persistence.securedb.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;

/** Реалізація репозиторія мов для бази {@link embasa.enums.DataBase#SECURE_DB} */
public class LanguageRepositoryImpl extends embasa.persistence.common.repository.impl.LanguageRepositoryImpl<Language>
        implements LanguageRepository {

    @Override
    public Language mapRow(ResultSet rs, int rowNum) throws SQLException {
        Language language = new Language();
        language.setLangCode(rs.getString("code"));
        language.setDescription(rs.getString("description"));
        language.setLanguage(rs.getString("language"));
        return language;
    }

    /**
     * Встановити посилання на шаблон jdbc
     * @param jdbcTemplate встановлюване значення
     */
    @Autowired
    @Qualifier(value = "secureDBJdbcTemplate")
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}

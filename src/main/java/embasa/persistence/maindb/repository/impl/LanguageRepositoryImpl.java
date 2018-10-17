package embasa.persistence.maindb.repository.impl;

import embasa.persistence.common.model.Language;
import embasa.persistence.maindb.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;

/** Реалізація репозиторію мов бази {@link embasa.enums.DataBase#MAIN_DB} */
public class LanguageRepositoryImpl extends embasa.persistence.common.repository.impl.LanguageRepositoryImpl<Language> implements LanguageRepository {

    @Override
    public Language mapRow(ResultSet rs, int rowNum) throws SQLException {
        Language lang = new Language();
        lang.setDescription(rs.getString("description"));
        lang.setLanguage(rs.getString("language"));
        lang.setLangCode(rs.getString("code"));
        return lang;
    }

    /**
     * Встановити посилання на шаблон jdbc
     * @param jdbcTemplate встановлюване значення
     */
    @Autowired
    @Qualifier(value = "mainDBJdbcTemplate")
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}

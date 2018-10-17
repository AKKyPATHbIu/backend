package embasa.persistence.common.repository.impl;

import embasa.persistence.BaseJdbcRepositoryImpl;
import embasa.persistence.common.model.Language;
import embasa.persistence.common.repository.LanguageRepository;

/** Реалізація репозиторія мов. */
public abstract class LanguageRepositoryImpl<T extends Language>
        extends BaseJdbcRepositoryImpl<T, String> implements LanguageRepository<T> {

    /** Запит додавання запису. */
    private static String INSERT = "INSERT INTO msg_langs(code, description, language) VALUES(?, ?, ?)";

    /** Конструктор за замовчанням. */
    public LanguageRepositoryImpl() {
        super("msg_langs");
        pkName = "code";
    }

    @Override
    public void save(Language p) {
        jdbcTemplate.update(INSERT, p.getLangCode(), p.getDescription(), p.getLanguage());
    }

    @Override
    public String getFindByIdSql() {
        return "SELECT * FROM msg_langs WHERE UPPER(code) = UPPER(?)";
    }
}

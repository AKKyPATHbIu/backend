package embasa.persistence.common.repository;

import embasa.persistence.BaseJdbcRepository;
import embasa.persistence.common.model.Language;

/** Репозиторій мов. */
public interface LanguageRepository<T extends Language> extends BaseJdbcRepository<T, String> {
}

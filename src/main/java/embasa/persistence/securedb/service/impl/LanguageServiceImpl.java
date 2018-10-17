package embasa.persistence.securedb.service.impl;

import embasa.persistence.common.model.Language;
import embasa.persistence.common.service.LanguageService;
import embasa.persistence.securedb.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional("secureDBTransactionManager")
/** Реалізація сервісу мови інтерфейсу. */
public class LanguageServiceImpl implements LanguageService<Language> {

    /** Репозиторій. */
    private LanguageRepository repository;

    /**
     * Встановити посилання на репозиторій
     * @param repository нове значення посилання на репозиторій
     */
    @Autowired
    @Qualifier(value = "secureDBLanguageRepository")
    public void setRepository(LanguageRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Language> findAll() {
        return repository.findAll();
    }

    @Override
    public Language findById(String code) {
        return repository.findById(code);
    }
}

package embasa.persistence.common.service.impl;

import embasa.persistence.common.model.MsgValue;
import embasa.persistence.common.repository.MsgValueRepository;
import embasa.persistence.common.service.MsgValueService;

import java.util.List;

/** Реалізация сервісу ресурсів локалізації. */
public class MsgValueServiceImpl implements MsgValueService {

    /** Репозиторій ресурсів локалізації. */
    private MsgValueRepository repository;

    /**
     * Конструктор
     * @param repository репозиторій
     */
    public MsgValueServiceImpl(MsgValueRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<MsgValue> findByCode(String code) {
        return repository.findByCode(code);
    }

    @Override
    public MsgValue findByCodeAndLang(String code, String langCode) {
        List<MsgValue> resources = findByCode(code);
        MsgValue result = null;
        for(MsgValue value : resources) {
            if (value.getLangCode().equalsIgnoreCase(langCode)) {
                result = value;
                break;
            }
        }
        return result;
    }

    @Override
    public void save(MsgValue p) {
        repository.save(p);
    }

    @Override
    public void update(MsgValue p) {
        repository.update(p);
    }

    @Override
    public void delete(String code) {
        repository.delete(code);
    }

    @Override
    public boolean isExists(String code) {
        return repository.isExist(code);
    }
}

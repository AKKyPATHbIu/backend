package embasa.persistence.common.repository;

import embasa.persistence.BaseJdbcRepository;
import embasa.persistence.common.model.MsgValue;

import java.util.List;

/** Інтерфейс репозиторія ресурсів локалізації. */
public interface MsgValueRepository extends BaseJdbcRepository<MsgValue, String> {

    /**
     * Знайти за кодом ресурсу
     * @param code код ресурсу
     * @return перелік ресурсів за кодом
     */
    List<MsgValue> findByCode(String code);
}

package embasa.persistence.maindb.repository;

import embasa.persistence.BaseJdbcRepository;
import embasa.persistence.maindb.model.CardEntityAttr;

import java.util.List;

/** Репозиторій атрибуту сутності конструтора. */
public interface CardEntityAttrRepository extends BaseJdbcRepository<CardEntityAttr, Long> {

    /**
     * Отримати пов'язані атрибути сутності картки конструктора
     * @param entityId ідентифікатор сутності карти конструктора
     * @return пов'язані атрибути сутності картки конструктора
     */
    List<CardEntityAttr> getByCardEntity(Long entityId);
}

package embasa.persistence.maindb.service;

import embasa.persistence.BaseJdbcService;
import embasa.persistence.maindb.model.CardEntityAttr;

import java.util.List;

/** Інтерфейс атрибутів сутності конструктора. */
public interface CardEntityAttrService extends BaseJdbcService<CardEntityAttr, Long> {

    /**
     * Отримати пов'язані атрибути сутності картки конструктора
     * @param entityId ідентифікатор сутності карти конструктора
     * @return пов'язані атрибути сутності картки конструктора
     */
    List<CardEntityAttr> getByCardEntity(Long entityId);
}

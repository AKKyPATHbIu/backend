package embasa.persistence.maindb.service.impl;

import embasa.persistence.BaseJdbcServiceImpl;
import embasa.persistence.maindb.model.CardEntityAttr;
import embasa.persistence.maindb.repository.CardEntityAttrRepository;
import embasa.persistence.maindb.service.CardEntityAttrService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional("mainDBTransactionManager")
/** Реалізація сервісу атрибутів сутності конструктора. */
public class CardEntityAttrServiceImpl extends BaseJdbcServiceImpl<CardEntityAttr> implements CardEntityAttrService {

    @Override
    public List<CardEntityAttr> getByCardEntity(Long entityId) {
        return ((CardEntityAttrRepository) repository).getByCardEntity(entityId);
    }
}

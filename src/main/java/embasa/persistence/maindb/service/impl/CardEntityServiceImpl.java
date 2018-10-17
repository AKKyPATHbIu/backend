package embasa.persistence.maindb.service.impl;

import embasa.persistence.BaseJdbcServiceImpl;
import embasa.persistence.maindb.model.CardEntity;
import embasa.persistence.maindb.service.CardEntityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional("mainDBTransactionManager")
/** Реалізація інтерфейсу сутності конструктора. */
public class CardEntityServiceImpl extends BaseJdbcServiceImpl<CardEntity> implements CardEntityService {

}

package embasa.persistence.maindb.service.impl;

import embasa.persistence.common.repository.MsgValueRepository;
import embasa.persistence.common.service.impl.MsgValueServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional("mainDBTransactionManager")
public class MsgValueServiceMainImpl extends MsgValueServiceImpl {

    /**
     * Конструктор
     * @param repository репозиторій
     */
    public MsgValueServiceMainImpl(MsgValueRepository repository) {
        super(repository);
    }
}

package embasa.persistence.securedb.service.impl;

import embasa.persistence.common.repository.MsgValueRepository;
import embasa.persistence.common.service.impl.MsgValueServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional("secureDBTransactionManager")
public class MsgValueServiceSecureImpl extends MsgValueServiceImpl {

    /**
     * Конструктор
     * @param repository репозиторій
     */
    public MsgValueServiceSecureImpl(MsgValueRepository repository) {
        super(repository);
    }
}

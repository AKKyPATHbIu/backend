package embasa.persistence.securedb.service.impl;

import embasa.persistence.BaseJdbcServiceImpl;
import embasa.persistence.securedb.model.UserEcp;
import embasa.persistence.securedb.repository.UserEcpRepository;
import embasa.persistence.securedb.service.UserEcpService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional("secureDBTransactionManager")
/** Реалізація сервіса електронного підпису користувача. */
public class UserEcpServiceImpl extends BaseJdbcServiceImpl<UserEcp> implements UserEcpService {

    @Override
    public UserEcp findByEdsKey(byte[] key) {
        return ((UserEcpRepository) repository).findByEdsKey(key);
    }

    @Override
    public List<UserEcp> findByUser(Long userId) {
        return ((UserEcpRepository) repository).findByUser(userId);
    }
}

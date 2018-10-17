package embasa.persistence.securedb.service.impl;

import embasa.persistence.BaseJdbcServiceImpl;
import embasa.persistence.securedb.model.User;
import embasa.persistence.securedb.repository.UserRepository;
import embasa.persistence.securedb.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional("secureDBTransactionManager")
public class UserServiceImpl extends BaseJdbcServiceImpl<User> implements UserService {

    @Override
    public User findByLogin(String login) {
        return ((UserRepository) repository).findByLogin(login);
    }

    @Override
    public User findByEmail(String email) {
        return ((UserRepository) repository).findByEmail(email);
    }
}

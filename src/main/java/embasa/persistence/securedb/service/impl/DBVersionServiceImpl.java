package embasa.persistence.securedb.service.impl;

import embasa.persistence.securedb.model.DBVersion;
import embasa.persistence.securedb.repository.DBVersionRepository;
import embasa.persistence.securedb.service.DBVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional("secureDBTransactionManager")
public class DBVersionServiceImpl implements DBVersionService {

    /** Репозиторій версій бд. */
    private DBVersionRepository repository;

    @Autowired
    @Qualifier("secureDBDBVersionRepository")
    public void setRepository(DBVersionRepository repository) {
        this.repository = repository;
    }

    @Override
    public DBVersion findByVersion(String version) {
        return repository.findByVersion(version);
    }

    @Override
    public DBVersion findLastByApplyDate() {
        return repository.findLastByApplyDate();
    }

    @Override
    public List<DBVersion> findAll() {
        return repository.findAll();
    }
}

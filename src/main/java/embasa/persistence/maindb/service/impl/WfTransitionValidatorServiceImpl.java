package embasa.persistence.maindb.service.impl;

import embasa.persistence.maindb.model.WfTransitionValidator;
import embasa.persistence.maindb.repository.WfTransitionValidatorRepository;
import embasa.persistence.maindb.service.WfTransitionValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional("mainDBTransactionManager")
/** Реалізація сервіса валідаторів переходу статуса workflow. */
public class WfTransitionValidatorServiceImpl implements WfTransitionValidatorService {

    @Autowired
    @Qualifier(value = "mainDBWfTransitionValidatorRepository")
    WfTransitionValidatorRepository repository;

    @Override
    public List<WfTransitionValidator> findByTransition(Long id) {
        return repository.findByTransition(id);
    }

    @Override
    public void save(WfTransitionValidator p) {
        repository.save(p);
    }
}

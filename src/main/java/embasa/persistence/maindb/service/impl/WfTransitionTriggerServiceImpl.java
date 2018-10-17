package embasa.persistence.maindb.service.impl;

import embasa.persistence.maindb.model.WfTransitionTrigger;
import embasa.persistence.maindb.repository.WfTransitionTriggerRepository;
import embasa.persistence.maindb.service.WfTransitionTriggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional("mainDBTransactionManager")
/** Реалізація сервіса тригера переходу статуса workflow. */
public class WfTransitionTriggerServiceImpl implements WfTransitionTriggerService {

    @Autowired
    @Qualifier(value = "mainDBWfTransitionTriggerRepository")
    WfTransitionTriggerRepository repository;

    @Override
    public List<WfTransitionTrigger> findByTransition(Long id) {
        return repository.findByTransition(id);
    }

    @Override
    public void save(WfTransitionTrigger p) {
        repository.save(p);
    }
}

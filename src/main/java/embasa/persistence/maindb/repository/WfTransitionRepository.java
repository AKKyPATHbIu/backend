package embasa.persistence.maindb.repository;

import embasa.persistence.BaseJdbcRepository;
import embasa.persistence.maindb.model.WfTransition;

/** Репозиторій переходів статуса workflow. */
public interface WfTransitionRepository extends BaseJdbcRepository<WfTransition, Long> {
}

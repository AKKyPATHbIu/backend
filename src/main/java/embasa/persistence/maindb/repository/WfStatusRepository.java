package embasa.persistence.maindb.repository;

import embasa.persistence.BaseJdbcRepository;
import embasa.persistence.maindb.model.WfStatus;

/** Інтерфейс репозиторія статуса workflow. */
public interface WfStatusRepository extends BaseJdbcRepository<WfStatus, Long> {
}

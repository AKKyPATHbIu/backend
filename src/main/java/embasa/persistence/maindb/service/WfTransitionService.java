package embasa.persistence.maindb.service;

import embasa.persistence.BaseJdbcService;
import embasa.persistence.maindb.model.WfTransition;

/** Сервіс переходів статусів workflow. */
public interface WfTransitionService extends BaseJdbcService<WfTransition, Long> {
}

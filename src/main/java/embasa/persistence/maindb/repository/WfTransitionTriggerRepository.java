package embasa.persistence.maindb.repository;

import embasa.persistence.maindb.model.WfTransitionTrigger;

import java.util.List;

/** Репозиторій тригера переходу статуса workflow. */
public interface WfTransitionTriggerRepository {

    /**
     * Знайти всі пов'язані з переходом тригери
     * @param id ідентифікатор переходу
     * @return всі пов'язані з переходом тригери
     */
    List<WfTransitionTrigger> findByTransition(Long id);

    /**
     * Зберегти сутність
     * @param p сутність
     */
    void save(WfTransitionTrigger p);
}

package embasa.persistence.maindb.repository;

import embasa.persistence.maindb.model.WfTransitionValidator;

import java.util.List;

/** Репозиторій валідатора переходу статуса workflow. */
public interface WfTransitionValidatorRepository {

    /**
     * Знайти всі пов'язані з переходом валідатори
     * @param id ідентифікатор переходу
     * @return всі пов'язані з переходом валідатори
     */
    List<WfTransitionValidator> findByTransition(Long id);

    /**
     * Зберегти сутність
     * @param p сутність
     */
    void save(WfTransitionValidator p);
}

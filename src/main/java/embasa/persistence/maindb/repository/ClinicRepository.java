package embasa.persistence.maindb.repository;

import embasa.persistence.BaseJdbcRepository;
import embasa.persistence.maindb.model.Clinic;

/** Репозиторій медичних закладів {@link Clinic} */
public interface ClinicRepository extends BaseJdbcRepository<Clinic, Long> {
}

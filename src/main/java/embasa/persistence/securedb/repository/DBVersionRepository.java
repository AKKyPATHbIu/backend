package embasa.persistence.securedb.repository;

import embasa.persistence.BaseJdbcRepository;
import embasa.persistence.securedb.model.DBVersion;

/** Інтерфейс репозиторія версій бд. */
public interface DBVersionRepository extends BaseJdbcRepository<DBVersion, String> {

    /**
     * Знайти за значенням версії
     * @param version версія
     * @return відповідний об'єкт
     */
    DBVersion findByVersion(String version);

    /**
     * Знайти останню версію за датою активації
     * @return остання версія за датою активації
     */
    DBVersion findLastByApplyDate();
}

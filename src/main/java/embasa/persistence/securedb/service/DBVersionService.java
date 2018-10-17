package embasa.persistence.securedb.service;

import embasa.persistence.securedb.model.DBVersion;

import java.util.List;

/** Сервіс сутностей змін версій бд. */
public interface DBVersionService {

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

    /**
     * Отримати всі записи змін версій бд
     * @return всі записи змін версій бд
     */
    List<DBVersion> findAll();
}

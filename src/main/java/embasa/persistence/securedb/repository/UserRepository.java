package embasa.persistence.securedb.repository;

import embasa.persistence.BaseJdbcRepository;
import embasa.persistence.securedb.model.User;

/** Інтерфейс репозиторія користувачів. */
public interface UserRepository extends BaseJdbcRepository<User, Long> {

    /**
     * Знайти {@link embasa.persistence.securedb.model.User} за ім'ям користувача.
     * @param login ім'я користувача
     * @return відповідний об'єкт {@link embasa.persistence.securedb.model.User} або null якщо запис не знайдено в базі
     */
    User findByLogin(String login);

    /**
     * Знайти {@link embasa.persistence.securedb.model.User} за електронною адресою користувача.
     * @param email електронна адреса користувача
     * @return відповідний об'єкт {@link embasa.persistence.securedb.model.User} або null якщо запис не знайдено в базі
     */
    User findByEmail(String email);
}

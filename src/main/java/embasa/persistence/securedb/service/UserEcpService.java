package embasa.persistence.securedb.service;

import embasa.persistence.BaseJdbcService;
import embasa.persistence.securedb.model.UserEcp;

import java.util.List;

/** Сервіс електронного підпису користувача. */
public interface UserEcpService extends BaseJdbcService<UserEcp, Long> {

    /**
     * Знайти {@link UserEcp} за приватним ключем.
     * @param key приватний ключ
     * @return  відповідний об'єкт {@link UserEcp} або null якщо запис не знайдено в базі
     */
    UserEcp findByEdsKey(byte[] key);

    /**
     * Знайти всі {@link UserEcp} пов'язані з користувачем.
     * @param userId ідентифікатор користувача
     * @return всі {@link UserEcp} пов'язані з користувачем.
     */
    List<UserEcp> findByUser(Long userId);
}

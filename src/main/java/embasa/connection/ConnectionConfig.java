package embasa.connection;

/**
 * Конфігурація конекта до бази даних
 */
public interface ConnectionConfig {

    /**
     * Отримати діалект
     * @return діалект
     */
    String getDialect();

    /**
     * Отримати драйвер
     * @return драйвер
     */
    String getDriver();

    /**
     * Отримати url хоста бази даних
     * @return url хоста бази даних
     */
    String getUrl();

    /**
     * Отримати ім'я користувача
     * @return ім'я користувача
     */
    String getUsername();

    /**
     * Отримати пароль
     * @return пароль
     */
    String getPassword();
}

package embasa.connection;

/**
 * Створення налаштувань конекта для баз даних
 */
public interface ConnectionCreator {

    /**
     * Створити налаштування конекта до бази maindb
     * @return налаштування конекта до бази maindb
     */
    ConnectionConfig createMainDBConfiguration();

    /**
     * Створити налаштування конекта до бази securedb
     * @return налаштування конекта до бази securedb
     */
    ConnectionConfig createSecureDBConfiguration();
}

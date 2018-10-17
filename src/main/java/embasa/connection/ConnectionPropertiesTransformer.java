package embasa.connection;

import java.util.Properties;

/**
 * Перетворення налаштувань конекта до баз даних
 */
public interface ConnectionPropertiesTransformer {

    /**
     * Перетворити параметри конекта до бази даних<br>
     * @param props вхідні параметри для створення конекту
     * <br>
     * <br>Приклад:
     * <br>connection.dialect=POSTGRESQL
     * <br>connection.url=localhost:5432/db
     * <br>connection.username=supervisor
     * <br>connection.password=S3CRET
     * <br><br>
     * @return параметри конекта до бази даних
     * <br>
     * <br>Приклад:
     * <br>hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
     * <br>hibernate.connection.driver_class=org.postgresql.Driver
     * <br>hibernate.connection.url=jdbc:postgresql://localhost:5432/db
     * <br>hibernate.connection.username=supervisor
     * <br>hibernate.connection.password=S3CRET
     * <br>hibernate.current_session_context_class=thread
     */
    ConnectionConfig transform(Properties props);

    /** Префікс ключа параметрів з properties-файла. */
    String PREFIX = "connection.";

    /** Найменування ключа діалекту. */
    String CONNECTION_DIALECT = PREFIX + "dialect";

    /** Найменування ключа url хоста бази даних. */
    String CONNECTION_URL = PREFIX + "url";

    /** Найменування ключа імені користувача. */
    String CONNECTION_USERNAME = PREFIX + "username";

    /** Найменування ключа паролю. */
    String CONNECTION_PASSWORD = PREFIX + "password";
}

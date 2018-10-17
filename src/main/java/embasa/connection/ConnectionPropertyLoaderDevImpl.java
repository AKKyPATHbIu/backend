package embasa.connection;

import embasa.enums.DataBase;
import embasa.enums.DBDialect;

import java.util.Properties;

import static embasa.connection.ConnectionPropertiesTransformer.*;

/**
 * Реалізація створення налаштувань конекту до бази даних з property-файлу для тестів
 */
public class ConnectionPropertyLoaderDevImpl implements ConnectionPropertyLoaderFromFile {

    /** Роздільник найменування бази і початкових властивостей. */
    public static String DELIM = ".";

    /** Урл хоста бази данних. */
    public static String HOST_URL = "dev03.skydigitallab.com:5432/";

    /** Діалект {@link DBDialect#POSTGRESQL}. */
    public static String DIALECT = DBDialect.POSTGRESQL.getDialectShort();

    /** Им'я бази maindb. */
    public static String MAIN_DB = "maindb";

    /** Им'я користувача бази maindb. */
    public static String MAIN_DB_USER = "app_embas";

    /** Пароль користувача бази maindb. */
    public static String MAIN_DB_PASS = "App_embas$1979";

    /** Им'я бази securedb. */
    public static String SECURE_DB = "securedb";

    /** Им'я користувача бази securedb. */
    public static String SECURE_DB_USER = "app_secembas";

    /** Пароль користувача бази securedb. */
    public static String SECURE_DB_PASS = "App_secembas$1979";

    @Override
    public Properties createFromFile(String fileName) {
        String maindb = DataBase.MAIN_DB.getDbName();
        String securedb = DataBase.SECURE_DB.getDbName();
        Properties props = new Properties();
        props.setProperty(wrapParamName(maindb, CONNECTION_URL), HOST_URL + MAIN_DB);
        props.setProperty(wrapParamName(maindb, CONNECTION_DIALECT), DIALECT);
        props.setProperty(wrapParamName(maindb, CONNECTION_USERNAME), MAIN_DB_USER);
        props.setProperty(wrapParamName(maindb, CONNECTION_PASSWORD), MAIN_DB_PASS);
        props.setProperty(wrapParamName(securedb, CONNECTION_URL), HOST_URL + SECURE_DB);
        props.setProperty(wrapParamName(securedb, CONNECTION_DIALECT), DIALECT);
        props.setProperty(wrapParamName(securedb, CONNECTION_USERNAME), SECURE_DB_USER);
        props.setProperty(wrapParamName(securedb, CONNECTION_PASSWORD), SECURE_DB_PASS);
        return props;
    }

    /**
     * Додати найменування бази до назви параметру
     * @param dbName найменування бази даних
     * @param propertyName початкове найменуваня параметру
     * @return найменування параметра з базою даних через роздільник
     */
    private String wrapParamName(String dbName, String propertyName) {
        StringBuilder propBuilder = new StringBuilder();
        propBuilder.append(dbName).append(DELIM).append(propertyName);
        return propBuilder.toString();
    }
}

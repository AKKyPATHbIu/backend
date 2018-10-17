package embasa.connection;

import embasa.enums.DataBase;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.Properties;

/**
 * Реалізація інтерфейсу створення конфігурації конекта до бази даних
 */
public class ConnectionCreatorImpl implements ConnectionCreator {

    private Logger logger = Logger.getLogger(ConnectionCreatorImpl.class);

    /** Конфігурація конектів до баз даних з файла-properties. */
    private final Properties fileConfig;

    /** Перетворювач налаштувань конекта з property-файла до налаштувань hibernate. */
    private final ConnectionPropertiesTransformer conPropsTransformer;

    /** Повне ім'я конфігураційного файлу. */
    private final String confFileName = System.getProperty("catalina.base") + File.separator +
            "conf" + File.separator + "connection.properties";

    /**
     * Конструктор
     * @param propertyLoader завантажувач налаштувань з файла-properties
     * @param conPropsTransformer перетворювач налаштувань з properties в конфігурацію hibernate
     */
    public ConnectionCreatorImpl(@Autowired ConnectionPropertyLoaderFromFile propertyLoader,
                                 @Autowired ConnectionPropertiesTransformer conPropsTransformer) {
        fileConfig = propertyLoader.createFromFile(confFileName);
        this.conPropsTransformer = conPropsTransformer;
    }

    @Override
    public ConnectionConfig createMainDBConfiguration() { return getHibernateConfigFor(DataBase.MAIN_DB); }

    @Override
    public ConnectionConfig createSecureDBConfiguration() {
        return getHibernateConfigFor(DataBase.SECURE_DB);
    }

    /**
     * Отримати налаштування конекта до баз даних для hibernate
     * @param dataBase база, для якої формуються налаштування
     * @return налаштування конекта до відповідної бази даних
     */
    private ConnectionConfig getHibernateConfigFor(DataBase dataBase) {
        return conPropsTransformer.transform(getConfigWithoutDBNameFor(dataBase));
    }

    /**
     * Отримати налаштування конекта до базы без префіксів бази даних в назві ключів
     * (видаляє ім'я бази з ключів)
     * @param dataBase база, для якої необхідно отримати налаштування конекту
     * @return налаштування конекту до бази даних
     */
    private Properties getConfigWithoutDBNameFor(DataBase dataBase) {
        Properties result = new Properties();
        String dbName = dataBase.getDbName();
        for (String propertyName : fileConfig.stringPropertyNames()) {
            if (propertyName.startsWith(dbName)) {
                if (propertyName.length() > dbName.length() + 1) {
                    String key = propertyName.substring(dbName.length() + 1);
                    result.setProperty(key, fileConfig.getProperty(propertyName));
                } else {
                    logger.error(String.format("Помилка в імені параметра конфігурації конекта до бази, параметр: %s",
                            propertyName));
                    break;
                }
            }
        }
        return result;
    }
}

package embasa.connection;

import embasa.enums.DataBase;
import embasa.enums.DBDialect;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static embasa.connection.ConnectionPropertiesTransformer.*;
import static embasa.connection.ConnectionPropertyLoaderDevImpl.*;

/** Утиліта тестування завантаження налаштувань конфігурації конекта. */
public class ConnectionPropertyLoaderUtil {

    private static Logger logger = Logger.getLogger(ConnectionPropertyLoaderUtil.class);

    /**
     * Отримати конфігурацію конекта для бази {@link embasa.enums.DataBase#MAIN_DB}
     * @return конфігурація конекта для бази {@link embasa.enums.DataBase#MAIN_DB}
     */
    public static Map<String, String> getMainDBConfig() {
        return getConfig(DataBase.MAIN_DB.getDbName(), HOST_URL + MAIN_DB, MAIN_DB_USER, MAIN_DB_PASS);
    }

    /**
     * Отримати конфігурацію конекта для бази {@link embasa.enums.DataBase#SECURE_DB}
     * @return конфігурація конекта для бази {@link embasa.enums.DataBase#SECURE_DB}
     */
    public static Map<String, String> getSecureDBConfig() {
        return getConfig(DataBase.SECURE_DB.getDbName(),HOST_URL + SECURE_DB, SECURE_DB_USER, SECURE_DB_PASS);
    }

    /**
     * Отримати конфігурацію конекта для бд
     * @param dbName наіменування бази даних
     * @param username ім'я користувача
     * @param password пароль
     * @return конфігурація конекта для бд
     */
    private static Map<String, String> getConfig(String dbName, String url, String username, String password) {
        Map<String, String> secureDBConfig = new HashMap<>();
        secureDBConfig.put(dbName + DELIM + CONNECTION_DIALECT,
                DBDialect.POSTGRESQL.getDialectShort());
        secureDBConfig.put(dbName + DELIM + CONNECTION_URL, url);
        secureDBConfig.put(dbName + DELIM + CONNECTION_USERNAME, username);
        secureDBConfig.put(dbName + DELIM + CONNECTION_PASSWORD, password);
        return secureDBConfig;
    }

    /**
     * Перевірити відповідність ключів конфігурацій
     * @param props конфігурація конектів до баз
     * @param mainDBConfig конфігурація конекта до бази {@link embasa.enums.DataBase#MAIN_DB}
     * @param secureDBConfig конфігурація конекта до бази {@link embasa.enums.DataBase#SECURE_DB}
     */
    public static void testConfig(Properties props, Map<String, String> mainDBConfig, Map<String, String> secureDBConfig) {
        assertNotNull(props);
        assertFalse(props.isEmpty());
        assertTrue(props.stringPropertyNames().size() == mainDBConfig.size() * 2);

        for (Map.Entry<String, String> ent : mainDBConfig.entrySet()) {
            String keyName = ent.getKey();
            String keyValue = ent.getValue();
            assertEquals(keyValue, props.getProperty(keyName));
        }

        for (Map.Entry<String, String> ent : secureDBConfig.entrySet()) {
            String keyName = ent.getKey();
            String keyValue = ent.getValue();
            assertEquals(keyValue, props.getProperty(keyName));
        }
    }

    /**
     * Створити тичасовий файл з конфігурацією конектів до баз даних
     * @param fileName им'я файла
     * @param fileExt розширення файла
     */
    public static String createTempConfigFile(String fileName, String fileExt) {
        Map<String, String> mainDBConfig = getMainDBConfig();
        Map<String, String> secureDBConfig = getSecureDBConfig();
        try {
            File tempFile = File.createTempFile(fileName, fileExt);
            tempFile.deleteOnExit();

            FileOutputStream fos = new FileOutputStream(tempFile);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            for (Map<String, String> config : new Map[] { mainDBConfig, secureDBConfig }) {
                for (Map.Entry<String, String> ent : config.entrySet()) {
                    bw.write(ent.getKey() + "=" + ent.getValue());
                    bw.newLine();
                }
            }
            bw.close();
            return tempFile.getAbsolutePath();
        } catch (IOException ex) {
            logger.error(ex);
        }
        return null;
    }
}

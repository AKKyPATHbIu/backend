package embasa.connection;

import embasa.config.RootConfig;
import embasa.enums.DataBase;
import embasa.enums.DBDialect;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Properties;

import static embasa.connection.ConnectionPropertyLoaderDevImpl.DELIM;
import static embasa.connection.ConnectionPropertiesTransformer.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class,
        classes = { RootConfig.class })
@ActiveProfiles("DEV")
public class ConnectionCreatorImplTest {

    @Autowired
    private ConnectionCreator dbConCreator;

    @Test
    public void createMainDBConfiguration() {
        assertNotNull(dbConCreator);
        ConnectionConfig mainDBCreatedConfig = dbConCreator.createMainDBConfiguration();
        testConfigKeys(mainDBCreatedConfig);
        Properties mainDBFileConfig = new Properties();
        mainDBFileConfig.putAll(ConnectionPropertyLoaderUtil.getMainDBConfig());
        testConfigValues(DataBase.MAIN_DB, mainDBFileConfig, mainDBCreatedConfig);
    }

    @Test
    public void createSecureDBConfiguration() {
        assertNotNull(dbConCreator);
        ConnectionConfig secureDBCreatedConfig = dbConCreator.createSecureDBConfiguration();
        testConfigKeys(secureDBCreatedConfig);
        Properties secureDBFileConfig = new Properties();
        secureDBFileConfig.putAll(ConnectionPropertyLoaderUtil.getSecureDBConfig());
        testConfigValues(DataBase.SECURE_DB, secureDBFileConfig, secureDBCreatedConfig);
    }

    /**
     * Перевірити ключі конфігурації
     * @param config конфігурація
     */
    private void testConfigKeys(ConnectionConfig config) {
        assertNotNull(config.getDialect());
        assertNotNull(config.getDriver());
        assertNotNull(config.getUrl());
        assertNotNull(config.getUsername());
        assertNotNull(config.getPassword());
    }

    /**
     * Перевірити значення ключів конфігурацій в файлі та створеною біном
     * @param dataBase база даних
     * @param fileConfig конфіг з початкового файла
     * @param createdConfig сформований конфіг з файла для бази
     */
    private void testConfigValues(DataBase dataBase, Properties fileConfig, ConnectionConfig createdConfig) {
        String dbName = dataBase.getDbName();

        String urlKey = dbName + DELIM + CONNECTION_URL;
        String dialectKey = dbName + DELIM + CONNECTION_DIALECT;
        String usernameKey = dbName + DELIM + CONNECTION_USERNAME;
        String passwordKey = dbName + DELIM + CONNECTION_PASSWORD;

        String dialect = fileConfig.getProperty(dialectKey);
        DBDialect hibDialect = DBDialect.getObjectBy(dialect);
        String url = hibDialect.getUrlPrefix() + fileConfig.getProperty(urlKey);
        String username = fileConfig.getProperty(usernameKey);
        String password = fileConfig.getProperty(passwordKey);

        assertEquals(url, createdConfig.getUrl());
        assertEquals(hibDialect.getDialect(), createdConfig.getDialect());
        assertEquals(username, createdConfig.getUsername());
        assertEquals(password, createdConfig.getPassword());
    }
}
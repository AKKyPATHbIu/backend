package embasa.connection;

import embasa.config.RootConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class,
        classes = { RootConfig.class })
@ActiveProfiles("DEV")
public class ConnectionPropertyLoaderDevImplTest {

    @Autowired
    ConnectionPropertyLoaderFromFile propertyLoader;

    /** Мапа з ключами конфыгурацыъ конекта до бази {@link embasa.enums.DataBase#MAIN_DB} */
    private final Map<String, String> mainDBConfig = ConnectionPropertyLoaderUtil.getMainDBConfig();

    /** Мапа з ключами конфыгурацыъ конекта до бази {@link embasa.enums.DataBase#SECURE_DB} */
    private final Map<String, String> secureDBConfig = ConnectionPropertyLoaderUtil.getSecureDBConfig();

    /** Тестувати бін завантаження налаштувань для конфігурації DEV. */
    @Test
    public void testPropertyLoaderTest() {
        assertTrue(propertyLoader instanceof ConnectionPropertyLoaderDevImpl);
        Properties props = propertyLoader.createFromFile("");
        ConnectionPropertyLoaderUtil.testConfig(props, mainDBConfig, secureDBConfig);
    }
}
package embasa.connection;

import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionPropertyLoaderProductionImplTest {

    @Mock
    Appender appender;

    @Captor
    ArgumentCaptor<LoggingEvent> logCaptor;

    ConnectionPropertyLoaderFromFile propertyLoader = new ConnectionPropertyLoaderProductionImpl();

    /** Повне імя файла з конфігурацією конекта до бази {@link embasa.enums.DataBase#MAIN_DB}. */
    private String fullConfFileName;

    /** Мапа з ключами конфігурації конекта до бази {@link embasa.enums.DataBase#MAIN_DB}. */
    Map<String, String> mainDBConfig = ConnectionPropertyLoaderUtil.getMainDBConfig();

    /** Мапа з ключами конфігурації конекта до бази {@link embasa.enums.DataBase#SECURE_DB}. */
    Map<String, String> secureDBConfig = ConnectionPropertyLoaderUtil.getSecureDBConfig();

    @Before
    public void createConfigFile() {
        fullConfFileName = ConnectionPropertyLoaderUtil.createTempConfigFile("connection", ".properties");
        Logger.getRootLogger().addAppender(appender);
    }

    @Test
    public void createFromFile() {
        assertNotNull(fullConfFileName);
        Properties props = propertyLoader.createFromFile(fullConfFileName);
        ConnectionPropertyLoaderUtil.testConfig(props, mainDBConfig, secureDBConfig);

        propertyLoader.createFromFile(fullConfFileName + "_1_2_3_4_5_");
        verify(appender, times(1)).doAppend(logCaptor.capture());
        LoggingEvent loggingEvent = logCaptor.getAllValues().get(0);
        assertTrue(loggingEvent.getMessage() instanceof FileNotFoundException);
    }

    @After
    public void cleanUp() {
        Logger.getRootLogger().removeAppender(appender);
    }
}
package embasa.connection;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;

/** Реалізація створення налаштувань конекта до бази даних з property-файла для продакшена. */
public class ConnectionPropertyLoaderProductionImpl implements ConnectionPropertyLoaderFromFile {

    private Logger logger = Logger.getLogger(ConnectionPropertyLoaderProductionImpl.class);

    @Override
    public Properties createFromFile(String fileName) {
        return readFromFile(fileName);
    }

    /**
     * Зчитати налаштування з файлу
     * @param fileName ім'я файлу з налаштуваннями
     * @return налаштування з файлу
     */
    private Properties readFromFile(String fileName) {
        File configFile = new File(fileName);
        Properties props = new Properties();
        try {
            InputStream stream = new FileInputStream(configFile);
            props.load(stream);
        } catch (IOException ex) {
            logger.error(ex);
        }
        return props;
    }
}

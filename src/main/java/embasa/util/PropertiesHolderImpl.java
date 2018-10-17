package embasa.util;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/** Утримувач властивостей додатку. */
public class PropertiesHolderImpl implements PropertiesHolder {

    /** Шлях до файлу з властивостями. */
    private final String PROPERTIES_FILE_PATH = "/project.properties";

    /** Властивості додатку. */
    private final Properties props;

    private static Logger logger = Logger.getLogger(PropertiesHolderImpl.class);

    /** Конструктор за замовчанням. */
    public PropertiesHolderImpl() {
        props = readProperties();
    }

    /**
     * Прочитати властивості додатку з файлу властивостей
     * @return властивості додатку
     */
    private Properties readProperties() {
        Properties result = new Properties();
        InputStream stream = getClass().getResourceAsStream(PROPERTIES_FILE_PATH);
        if (stream != null) {
            try {
                result.load(stream);
            } catch (IOException e) {
                logger.error(e);
            } finally {
                try {
                    stream.close();
                } catch (IOException ex) {
                    logger.error(ex);
                }
            }
        }
        return result;
    }

    @Override
    public String get(String propertyName) {
        return props.getProperty(propertyName);
    }
}

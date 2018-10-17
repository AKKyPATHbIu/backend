package embasa.connection;

import embasa.enums.DBDialect;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import java.util.Properties;

/**
 * Реалізація інтерфейсу створення налаштувань конекта до бази даних
 */
public class ConnectionPropertiesTransformerImpl implements ConnectionPropertiesTransformer {

    private Logger logger = Logger.getLogger(ConnectionPropertiesTransformerImpl.class);

    @Override
    public ConnectionConfig transform(Properties props) {
        ConnectionConfigImpl config = new ConnectionConfigImpl();

        String dialect = props.getProperty(CONNECTION_DIALECT);
        String url = props.getProperty(CONNECTION_URL);

        String errMsg = "Трапилася помилка при завантаженні налаштувань конекта до баз даних: connection.properties";

        if (!StringUtils.hasText(dialect)) {
            logger.error(errMsg + ", не знайдено параметр connection.dialect!");
        } else if (!StringUtils.hasText(url)) {
            logger.error(errMsg + ", не знайдено параметр connection.url!");
        } else {
            DBDialect hDialect = DBDialect.getObjectBy(dialect);
            if (hDialect != null) {
                config.setDialect(hDialect.getDialect())
                        .setDriver(hDialect.getDriver())
                        .setUrl(hDialect.getUrlPrefix() + url)
                        .setUsername(props.getProperty(CONNECTION_USERNAME))
                        .setPassword(props.getProperty(CONNECTION_PASSWORD));
            } else {
                logger.error(errMsg + ", діалект: " + dialect + " не підтримується системою!");
            }
        }
        return config;
    }
}

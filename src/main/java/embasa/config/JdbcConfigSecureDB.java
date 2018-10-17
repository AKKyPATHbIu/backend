package embasa.config;

import com.zaxxer.hikari.HikariDataSource;
import embasa.connection.ConnectionConfig;
import embasa.connection.ConnectionCreator;
import embasa.enums.DataBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/** Конфігурація jpa для бази {@link DataBase#SECURE_DB}. */
@Configuration
public class JdbcConfigSecureDB {

    /** Конфігурація налаштувань конекта до бази даних.*/
    private final ConnectionConfig dbConfig;

    /**
     * Конструктор
     * @param connectionCreator об'єкт створення налаштувань конекта до бази даних
     */
    public JdbcConfigSecureDB(@Autowired ConnectionCreator connectionCreator) {
        dbConfig = connectionCreator.createSecureDBConfiguration();
    }

    @Bean(name = "secureDBDataSource", destroyMethod = "close")
    public HikariDataSource secureDBDataSource() {
        return DataSourceConfigUtil.dataSource(dbConfig, DataBase.SECURE_DB);
    }

    @Bean(name = "secureDBTransactionManager")
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(secureDBDataSource());
        return transactionManager;
    }
}

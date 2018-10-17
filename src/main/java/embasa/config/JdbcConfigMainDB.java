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

/** Конфігурація jdbc для бази {@link DataBase#MAIN_DB}. */
@Configuration
public class JdbcConfigMainDB {

    /** Конфігурація налаштувань конекта до бази даних.*/
    private final ConnectionConfig dbConfig;

    /**
     * Конструктор
     * @param connectionCreator об'єкт створення налаштувань конекта до бази даних
     */
    public JdbcConfigMainDB(@Autowired ConnectionCreator connectionCreator) {
        dbConfig = connectionCreator.createMainDBConfiguration();
    }

    @Bean(name = "mainDBDataSource", destroyMethod = "close")
    public HikariDataSource mainDBDataSource() {
        return DataSourceConfigUtil.dataSource(dbConfig, DataBase.MAIN_DB);
    }

    @Bean(name = "mainDBTransactionManager")
    public PlatformTransactionManager transactionManager() {
        org.springframework.jdbc.datasource.DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(mainDBDataSource());
        return transactionManager;
    }
}

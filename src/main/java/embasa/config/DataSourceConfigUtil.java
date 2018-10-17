package embasa.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import embasa.connection.ConnectionConfig;
import embasa.enums.DataBase;

/** Допоміжна утиліта створення джерела даних. */
public class DataSourceConfigUtil {

    /**
     * Отримати джерело даних
     * @param config конфігурація підключення до бази даних
     * @return джерело даних
     */
    public static HikariDataSource dataSource(ConnectionConfig config, DataBase dataBase) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(config.getUrl());
        hikariConfig.setUsername(config.getUsername());
        hikariConfig.setPassword(config.getPassword());
        hikariConfig.setDriverClassName(config.getDriver());
        hikariConfig.setConnectionTimeout(20000);
        hikariConfig.setMinimumIdle(5);
        hikariConfig.setMaximumPoolSize(20);
        hikariConfig.setIdleTimeout(30000);
        hikariConfig.addDataSourceProperty( "cachePrepStmts" , "true" );
        hikariConfig.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        hikariConfig.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        hikariConfig.setConnectionInitSql("COMMIT;");
        hikariConfig.setSchema(dataBase.getSchema());
        hikariConfig.setAutoCommit(false);
        return new HikariDataSource(hikariConfig);
    }
}

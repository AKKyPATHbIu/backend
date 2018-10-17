package embasa.enums;

/** Параметри конекта до БД. */
public enum DBDialect {
    POSTGRESQL("POSTGRESQL", "org.hibernate.dialect.PostgreSQLDialect", "org.postgresql.Driver", "jdbc:postgresql://"),
    MYSQL("MYSQL", "org.hibernate.dialect.MySQLDialect", "com.mysql.jdbc.Driver", "jdbc:mysql://");

    /** Коротке найменування діалекту. */
    private final String dialectShort;

    /** Діалект. */
    private final String dialect;

    /** Драйвер. */
    private final String driver;

    /** Префікс url-конекта. */
    private final String urlPrefix;

    /**
     * Конструктор
     * @param dialect діалект
     * @param driver драйвер
     * @param urlPrefix префікс url-а конекта
     */
    DBDialect(String shortDialect, String dialect, String driver, String urlPrefix) {
        this.dialectShort = shortDialect;
        this.dialect = dialect;
        this.driver = driver;
        this.urlPrefix = urlPrefix;
    }

    /**
     * Отримати найменування діалекта
     * @return найменування діалекта
     */
    public String getDialect() {
        return dialect;
    }

    /**
     * Отримати драйвер
     * @return драйвер
     */
    public String getDriver() {
        return driver;
    }

    /**
     * Отримати префікс url-а конекта
     * @return префікс url-а конекта
     */
    public String getUrlPrefix() {
        return urlPrefix;
    }

    /**
     * Отримати коротке найменування діалекта
     * @return коротке найменування діалекта
     */
    public String getDialectShort() {
        return dialectShort;
    }

    /**
     * Отримати об'єкт {@link DBDialect} по назві діалекта
     * @param dialect назва діалекта
     * @return відповідний назві об'єкт {@link DBDialect}
     */
    public static DBDialect getObjectBy(String dialect) {
        DBDialect result = null;
        for (DBDialect hd : DBDialect.values()) {
            if (hd.dialectShort.equalsIgnoreCase(dialect)) {
                result = hd;
            }
        }
        return result;
    }
}

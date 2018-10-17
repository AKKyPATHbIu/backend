package embasa.enums;

/** Бази даних. */
public enum DataBase {
    MAIN_DB("maindb", "embas"), SECURE_DB("securedb", "secembas");

    /** Найменування бази даних. */
    private final String dbName;

    /** Схема. */
    private final String schema;

    /**
     * Конструктор
     * @param dbName найменування бази даних
     */
    DataBase(String dbName, String schema) {
        this.dbName = dbName;
        this.schema = schema;
    }

    /**
     * Отримати найменування бази даних
     * @return найменування бази даних
     */
    public String getDbName() {
        return dbName;
    }

    /**
     * Отримати схему бази даних
     * @return схема бази даних
     */
    public String getSchema() {
        return schema;
    }
}

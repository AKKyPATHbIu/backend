package embasa.util;

/** Утримувач властивостей додатку. */
public interface PropertiesHolder {

    /** Ім'я параметру версія backend-у. */
    String VERSION = "version";

    /** Ім'я параметру мінімально допустимої версії бази даних. */
    String MIN_DB_VERSION = "minDbVersion";

    /** Ім'я параметру мінімально допустимої версії frontend-у. */
    String MIN_FRONTEND_VERSION = "minFrontendVersion";

    /**
     * Отримати властивість
     * @param propertyName ім'я властивості
     * @return властивість
     */
    String get(String propertyName);
}

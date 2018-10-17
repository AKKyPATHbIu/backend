package embasa.i18n;

import java.util.Locale;

/** Локалізація. */
public interface Localizer {

    /**
     * Отримати ресурс із встановленною локалізацією
     * @param key ключ ресурсу
     * @return ресурс із встановленною локалізацією
     */
    String getMessage(String key);

    /**
     * Отримати ресурс із заданою локалізацією
     * @param key ключ ресурсу
     * @param locale локалізація
     * @return ресурс із заданою локалізацією
     */
    String getMessage(String key, Locale locale);
}

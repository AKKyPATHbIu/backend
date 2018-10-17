package embasa.i18n;

import embasa.persistence.common.model.CommonLocalized;
import embasa.persistence.common.model.Language;

import java.util.Locale;

/**
 * Утиліта для інтернаціоналізації
 */
public class LocaleUtil {

    /** Локаль України. */
    public static Locale LOCALE_UA = new Locale("ua");

    /** Локаль Росії. */
    public static Locale LOCALE_RU = new Locale("ru");

    /**
     * Отримати об'єкт {@link java.util.Locale} за кодом
     * @param code код локалі
     * @return відповідний коду об'єкт {@link java.util.Locale}
     */
    public static Locale parseLocaleBy(String code) {
        if (code == null) {
            return LOCALE_UA;
        }
        String trimmedCode = code.trim();
        return "ru".equalsIgnoreCase(trimmedCode) ? LOCALE_RU :
                "en".equalsIgnoreCase(trimmedCode) ? Locale.ENGLISH : LOCALE_UA;
    }

    /**
     * Отримати локалізований ресурс для заданої мови
     * @param langCode код мови
     * @param value переклад ресурсу
     * @param languageHolder утримувач мов
     * @return локалізований ресурс для заданої мови
     */
    public static CommonLocalized getLocalized(String langCode, String value, LanguageHolder languageHolder) {
        Language language = languageHolder.getLanguageBy(langCode);
        CommonLocalized loc = new CommonLocalized(language);
        loc.setValue(value);
        return loc;
    }
}

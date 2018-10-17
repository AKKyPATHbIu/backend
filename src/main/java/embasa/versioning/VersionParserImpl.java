package embasa.versioning;

import embasa.i18n.Localizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.security.InvalidParameterException;
import java.util.Locale;

/** Реалізація парсера версії додатку. */
public class VersionParserImpl implements VersionParser {

    /** Регулярний вираз для валідації версії.  */
    private final String REG_EXP = "(\\d+\\.)(\\d+\\.)(\\d+)";

    /** Не задано код версії продукту! */
    public final String VERSION_IS_EMPTY_MSG = "versionIsEmpty";

    /** Помилкова структура версії продукту (%s)! */
    public final String BAD_VERSION_MSG = "badVersionStructure";

    /** Об'єкт локалізації. */
    private Localizer localizer;

    @Autowired
    public void setLocalizer(Localizer localizer) {
        this.localizer = localizer;
    }

    @Override
    public Version parse(String version, Locale locale) throws InvalidParameterException {
        checkVersion(version, locale);
        String[] v = version.split("\\.");
        int major = Integer.valueOf(v[0]);
        int minor = Integer.valueOf(v[1]);
        int patch = Integer.valueOf(v[2]);
        return new VersionImpl(major, minor, patch);
    }

    /**
     * Перевірити коректність версії в строковому вигляді
     * @param version версія в строковому вигляді
     * @throws InvalidParameterException
     */
    private void checkVersion(String version, Locale locale) throws IllegalArgumentException {
        if (!StringUtils.hasText(version)) {
            throw new IllegalArgumentException(localizer.getMessage(VERSION_IS_EMPTY_MSG, locale));
        }

        if (!version.matches(REG_EXP)) {
            throw new IllegalArgumentException(String.format(localizer.getMessage(BAD_VERSION_MSG, locale), version));
        }
    }
}

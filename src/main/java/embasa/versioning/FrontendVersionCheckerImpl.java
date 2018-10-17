package embasa.versioning;

import embasa.util.PropertiesHolder;

import java.util.Locale;

/** Реалізація перевірки сумісності версій frontend-у і backend-у. */
public class FrontendVersionCheckerImpl extends VersionCheckerBase implements FrontendVersionChecker {

    /**
     * Конструктор
     * @param versionParser парсер версіїї в строковому вигляді
     * @param propertiesHolder утримувач налаштувань додатку
     */
    public FrontendVersionCheckerImpl(VersionParser versionParser, PropertiesHolder propertiesHolder) {
        super(versionParser, propertiesHolder);
    }

    @Override
    public String getMinVersionPropertyKey() {
        return PropertiesHolder.MIN_FRONTEND_VERSION;
    }

    @Override
    public boolean isValid(String version, Locale locale) throws IllegalArgumentException {
        return isVersionsSuitable(minVersion, versionParser.parse(version, locale));
    }
}

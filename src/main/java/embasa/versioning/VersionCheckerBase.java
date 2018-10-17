package embasa.versioning;

import embasa.i18n.LocaleUtil;
import embasa.util.PropertiesHolder;

/** Базовий клас перевірки сумісності версій. */
public abstract class VersionCheckerBase {

    /** Парсер версіїї в строковому вигляді. */
    protected VersionParser versionParser;

    /** Мінімально допустима версія. */
    protected Version minVersion;

    /**
     * Конструктор
     * @param versionParser парсер версіїї в строковому вигляді
     * @param propertiesHolder утримувач налаштувань додатку
     */
    public VersionCheckerBase(VersionParser versionParser, PropertiesHolder propertiesHolder) {
        this.versionParser = versionParser;
        String version = propertiesHolder.get(getMinVersionPropertyKey());
        minVersion = versionParser.parse(version, LocaleUtil.LOCALE_UA);
    }

    /**
     * Отримати ім'я параметру мінімально допустимої версії
     * @return ім'я параметру мінімально допустимої версії
     */
    public abstract String getMinVersionPropertyKey();

    /**
     * Перевірити сумісність версій
     * @param minVersion мінімально допустима версія
     * @param compareVersion перевіряєма версія
     * @return true в разі сумісності версій
     */
    protected boolean isVersionsSuitable(Version minVersion, Version compareVersion) {
        return minVersion.before(compareVersion) || minVersion.equals(compareVersion);
    }
}

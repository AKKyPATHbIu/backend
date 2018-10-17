package embasa.versioning;

import java.util.Locale;

/** Парсер версії додатку. */
public interface VersionParser {

    /**
     * Розпарсити версію додатку із строки
     * @param version версія додатку в строковому вигляді
     * @param locale локаль
     * @return {@link Version} з строки
     * @throws IllegalArgumentException
     */
    Version parse(String version, Locale locale) throws IllegalArgumentException;
}

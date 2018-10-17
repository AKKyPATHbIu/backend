package embasa.versioning;

import java.util.Locale;

/** Перевірка сумісності версій frontend-у і backend-у. */
public interface FrontendVersionChecker {

    /**
     * Перевірити сумісність версії frontend-у і backend-у
     * @param version перевіряєма версія
     * @param locale локаль повідомлень в разі виключень
     * @return true в разі сумісності версій
     * @throws IllegalArgumentException
     */
    boolean isValid(String version, Locale locale) throws IllegalArgumentException;
}

package embasa;

import embasa.frontinteraction.Request;
import embasa.i18n.LanguageHolder;
import embasa.persistence.common.model.Language;
import embasa.persistence.common.service.LanguageService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/** Допоміжна утиліта. */
public class LangUtil {

    /**
     * Українська мова.
     */
    public static Language LANG_UA = buildLanguage("UA", "Українська", "мова");

    /**
     * Російська мова.
     */
    public static Language LANG_RU = buildLanguage("RU", "Русский", "язык");

    /**
     * Англійська мова.
     */
    public static Language LANG_EN = buildLanguage("EN", "English", "language");

    /**
     * Створити об'єкт мови
     *
     * @param code код мови
     * @param desc назва мови
     * @param lang слово мова на поточній мові
     * @return об'єкт мови
     */
    private static Language buildLanguage(String code, String desc, String lang) {
        Language result = new Language();
        result.setLangCode(code);
        result.setDescription(desc);
        result.setLanguage(lang);
        return result;
    }

    /**
     * Створити об'єкт запиту
     * @return об'єкт запиту
     */
    public static Request buildRequest() {
        Request request = new Request((LanguageHolder) code -> {
            if (LangUtil.LANG_UA.getLangCode().equalsIgnoreCase(code)) {
                return LangUtil.LANG_UA;
            } else if (LangUtil.LANG_RU.getLangCode().equalsIgnoreCase(code)) {
                return LangUtil.LANG_RU;
            } else if (LangUtil.LANG_EN.getLangCode().equalsIgnoreCase(code)) {
                return LangUtil.LANG_EN;
            } else {
                return null;
            }
        });
        return request;
    }

    /**
     * Тестувати метод пошуку за кодом мови
     * @param langService сервіс мов
     */
    public static void testFindById(LanguageService langService) {
        Language langUA = langService.findById("ua");
        assertNotNull(langUA);
        Language langUA1 = langService.findById("UA");
        assertEquals(langUA, langUA1);
    }
}

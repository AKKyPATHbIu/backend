package embasa.persistence.maindb.service.impl;

import embasa.persistence.common.model.MsgValue;
import embasa.persistence.common.service.MsgValueService;

/** Допоміжна утиліта. */
public class ServiceUtil {

    public static String LANG_CODE_UA = "UA";
    public static String LANG_CODE_RU = "RU";
    public static String LANG_CODE_EN = "EN";

    public static String VALUE_UA = "українська";
    public static String VALUE_RU = "русский";
    public static String VALUE_EN = "English";

    /**
     * Сворити в бд ресурси для кода
     * @param code код ресурсів
     * @param msgValueService сервіс ресурсів локалізації
     */
    public static void createLocalizeResources(String code, MsgValueService msgValueService) {
        addResources(msgValueService, code, LANG_CODE_UA, VALUE_UA);
        addResources(msgValueService, code, LANG_CODE_RU, VALUE_RU);
        addResources(msgValueService, code, LANG_CODE_EN, VALUE_EN);
    }

    /**
     * Створити ресурс для мови
     * @param msgValueService сервыс локалызацыъ ресурсыв
     * @param code коде ресурсу
     * @param langCode код мови
     * @param value ресурс
     */
    private static void addResources(MsgValueService msgValueService, String code, String langCode ,String value) {
        MsgValue msg = new MsgValue();
        msg.setCode(code);
        msg.setLangCode(langCode);
        msg.setValue(value);
        msgValueService.save(msg);
    }
}

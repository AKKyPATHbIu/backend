package embasa.persistence.common;

import embasa.persistence.common.model.Language;
import embasa.persistence.common.model.CommonLocalized;

import java.util.ArrayList;

/** Список ресурсів локалізації. */
public class LocalizedList extends ArrayList<CommonLocalized> {

    /** Код ресурсу. */
    private String code;

    /**
     * Отримати код ресурсу
     * @return код ресурсу
     */
    public String getCode() {
        return code;
    }

    /**
     * Встановити код ресурсу
     * @param code нове значення коду ресурсу
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Отримати переклад ресурсу для заданої мови
     * @param language мова, для якої необхідно отримати переклад ресурсу
     * @return переклад ресурсу для заданої мови
     */
    public String getResource(Language language) {
        String result = null;
        for (int i = 0; i < size(); i++) {
            Localizable loc = get(i);
            if (loc.getLanguage().equals(language)) {
                result = loc.getValue();
                break;
            }
        }
        return result == null ? (isEmpty() ? null : get(0).getValue()) : result;
    }
}

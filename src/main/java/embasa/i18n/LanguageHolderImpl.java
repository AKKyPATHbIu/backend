package embasa.i18n;

import embasa.persistence.common.model.Language;
import embasa.persistence.common.service.LanguageService;

import java.util.List;

/** Реалізація утримувача мов інтерфейсу. */
public class LanguageHolderImpl implements LanguageHolder {

    /** Перелік всіх підтримуваних системою мов. */
    private List<Language> allLanguages;

    /**
     * Конструктор
     * @param langService сервіс мов
     */
    public LanguageHolderImpl(LanguageService langService) {
        allLanguages = langService.findAll();
    }

    @Override
    public Language getLanguageBy(String code) {
        Language lang = null;
        for (Language l : allLanguages) {
            if (l.getLangCode().equalsIgnoreCase(code)) {
                lang = l;
                break;
            }
        }
        return lang;
    }
}

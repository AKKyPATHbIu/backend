package embasa.i18n;

import embasa.persistence.common.model.Language;

/** Утримувач доступних мов інтерфейсу. */
public interface LanguageHolder {

    /**
     * Отримати мову по коду
     * @param code код мови
     * @return відповідний коду мови об'єкт
     */
    Language getLanguageBy(String code);
}

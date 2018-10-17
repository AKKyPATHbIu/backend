package embasa.persistence.common;

import embasa.persistence.common.model.Language;

/** Ознака локалізованості ресурсу. */
public interface Localizable {

    /** Отримати мову. */
    Language getLanguage();

    /** Отримати локалізований ресурс. */
    String getValue();
}

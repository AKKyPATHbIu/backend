package embasa.persistence.common;

import embasa.persistence.common.model.Language;
import embasa.persistence.common.model.CommonLocalized;

/** Інтерфейс сутності з локалізованим полем опис. */
public interface Descable {

    /** Отримати код ресурса. */
    String getDescCode();

    /**
     * Додати ресурс опису.
     * @param loc локалізований ресурс
     */
    void addDescResource(CommonLocalized loc);

    /**
     * Отримати ресурс опису.
     * @param language мова, для якої необхідно отримати ресурс
     */
    String getDescResource(Language language);
}

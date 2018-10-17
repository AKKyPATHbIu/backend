package embasa.persistence.common;

import embasa.persistence.common.model.Language;
import embasa.persistence.common.model.CommonLocalized;

/** Інтерфейс сутності з локалізованим полем ім'я. */
public interface Nameable {

    /** Отримати код ресурса. */
    String getNameCode();

    /**
     * Додати ресурс імені.
     * @param loc локалізований ресурс
     */
    void addNameResource(CommonLocalized loc);

    /**
     * Отримати ресурс імені.
     * @param language мова, для якої необхідно отримати ресурс
     */
    String getNameResource(Language language);
}

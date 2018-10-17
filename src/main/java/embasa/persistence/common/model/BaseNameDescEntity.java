package embasa.persistence.common.model;

import embasa.persistence.common.Descable;
import embasa.persistence.common.LocalizedList;

/** Базовий клас сутності з локалізованими ім'ям та описом. */
public class BaseNameDescEntity<T> extends BaseNameEntity<T> implements Descable {

    /** Опис сутності. */
    protected final LocalizedList descr = new LocalizedList();

    /**
     * Отримати список локалізованих ресурсів опису.
     * @return список локалізованих ресурсів опису.
     */
    public LocalizedList getDescr() {
        return descr;
    }

    /**
     * Встановити код ресурсу опису
     * @param descCode встановлюване значення
     */
    public void setDescCode(String descCode) {
        descr.setCode(descCode);
    }

    @Override
    public String getDescCode() {
        return descr.getCode();
    }

    @Override
    public void addDescResource(CommonLocalized loc) {
        descr.add(loc);
    }

    @Override
    public String getDescResource(Language language) {
        return descr.getResource(language);
    }
}

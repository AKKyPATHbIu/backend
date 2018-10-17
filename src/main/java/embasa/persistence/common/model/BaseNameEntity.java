package embasa.persistence.common.model;

import embasa.persistence.common.LocalizedList;
import embasa.persistence.common.Nameable;

public class BaseNameEntity<T> extends BaseEntity<T> implements Nameable {

    /** Ім'я сутності. */
    protected final LocalizedList name = new LocalizedList();

    /**
     * Отримати список локалізованих ресурсів імені.
     * @return список локалізованих ресурсів імені.
     */
    @Override
    public String getNameCode() {
        return name.getCode();
    }

    /**
     * Встановити код ресурсу імені
     * @param nameCode встановлюване значення
     */
    public void setNameCode(String nameCode) {
        name.setCode(nameCode);
    }

    @Override
    public void addNameResource(CommonLocalized loc) {
        name.add(loc);
    }

    @Override
    public String getNameResource(Language language) {
        return name.getResource(language);
    }

    /**
     * Отримати список локалізованих ресурсів
     * @return список локалізованих ресурсів
     */
    public LocalizedList getName() {
        return name;
    }
}

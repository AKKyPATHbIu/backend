package embasa.persistence.common.model;

import embasa.persistence.common.Localizable;

import java.util.Objects;

/** Локалізованиий ресурс. */
public class CommonLocalized implements Localizable {

    /** Мова ресурсу. */
    private Language language;

    /** Переклад. */
    private String value;

    /**
     * Конструктор
     * @param language мова ресурсу
     */
    public CommonLocalized(Language language) {
        this.language = language;
    }

    @Override
    public Language getLanguage() {
        return language;
    }

    /**
     * Встановити значення перекладу
     * @param value нове значення перекладу ресурсу
     */
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(language.getLangCode(), value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof CommonLocalized)) {
            return false;
        }
        CommonLocalized that = (CommonLocalized) o;
        return this.language.getLangCode().equalsIgnoreCase(that.language.getLangCode()) &&
                (this.value == null ? that.value == null : this.value.equals(that.value));
    }
}

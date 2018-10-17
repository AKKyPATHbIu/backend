package embasa.persistence.common.model;

import embasa.i18n.LocaleUtil;

import java.io.Serializable;
import java.util.Locale;
import java.util.Objects;

/** Базовий клас мови інтерфейсу. */
public class Language implements Serializable {

    /** Код мови. */
    private String code;

    /** Назва мови. */
    private String description;

    /** Слово "мова" на поточній мові. */
    private String language;

    /** Локаль. */
    private transient Locale locale = LocaleUtil.LOCALE_UA;

    /** Конструктор за замовчанням. */
    public Language() {
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (code == null) {
            return false;
        } else if (o instanceof Language) {
            return this.code.equals(((Language) o).code);
        }
        return false;
    }

    /**
     * Отримати код мови
     * @return код мови
     */
    public String getLangCode() {
        return code;
    }

    /**
     * Встановити код мови
     * @param code нове значення коду мови
     */
    public void setLangCode(String code) {
        this.code = code;
        locale = LocaleUtil.parseLocaleBy(code);
    }

    /**
     * Отримати назву мови
     * @return назва мови
     */
    public String getDescription() {
        return description;
    }

    /**
     * Встановити назву мови
     * @param description нове значення назви
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Отримати значення слова "мова" на поточній мові
     * @return значення слова "мова" на поточній мові
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Встановити значення слова "мова" на поточній мові
     * @param language нове значення слова "мова" на поточній мові
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Отримати локаль
     * @return локаль
     */
    public Locale getLocale() {
        return locale;
    }
}

package embasa.persistence.common.model;

import java.util.Objects;

/** Ресурс локалізації. */
public class MsgValue {

    /** Код ресурсу. */
    private String code;

    /** Код мови. */
    private String langCode;

    /** Локалізоване значення ресурсу. */
    private String value;

    /** Конструктор за замовчанням. */
    public MsgValue() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLangCode() {
        return langCode;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, langCode);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (this.code == null || this.langCode == null || !(o instanceof MsgValue)) {
            return false;
        }

        MsgValue obj = (MsgValue) o;
        return code.equalsIgnoreCase(obj.code) && langCode.equalsIgnoreCase(obj.langCode);
    }
}

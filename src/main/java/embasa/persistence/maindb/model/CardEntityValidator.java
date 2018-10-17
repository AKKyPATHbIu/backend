package embasa.persistence.maindb.model;

import java.util.Objects;

/** Валідатор атрибутів сутності картки конструктора. */
public class CardEntityValidator {

    /** Валідатор. */
    private Validator validator;

    /** Параметри валідатора. */
    private String params;

    /** Конструктор за замовчанням. */
    public CardEntityValidator() {
    }

    public Validator getValidator() {
        return validator;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    @Override
    public int hashCode() {
        return Objects.hash(validator);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (validator == null || !(o instanceof CardEntityValidator)) {
            return false;
        }

        CardEntityValidator that = (CardEntityValidator) o;
        return this.validator.equals(that.validator);
    }
}

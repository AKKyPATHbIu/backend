package embasa.validators;

import embasa.persistence.maindb.model.CardEntity;

import java.util.List;

/** Валідатор сутності картки конструктора. */
public interface CardEntityValidator {

    /**
     * Валідувати сутність картки конструктора
     * @param entity сутніть, яку необхідно валідувати
     * @return список помилок (якщо сутність невалідна)
     */
    List<String> validate(CardEntity entity);
}

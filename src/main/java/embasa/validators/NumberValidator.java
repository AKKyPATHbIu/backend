package embasa.validators;

/** Числовий валідатор. */
public interface NumberValidator<T extends Number> extends BaseValidator<T> {

    /**
     * Більше ніж
     * @param validatedValue значення для валідації
     * @param value значення з яким порівнюється
     * @return true якщо testedValue > value
     */
    boolean isGreaterThan(T validatedValue, T value);


    /**
     * Більше ніж чи дорівнює
     * @param validatedValue значення для валідації
     * @param value значення з яким порівнюється
     * @return true якщо testedValue >= value
     */
    boolean isGreaterOrEquals(T validatedValue, T value);

    /**
     * Дорівнює
     * @param validatedValue значення для валідації
     * @param value значення з яким порівнюється
     * @return true якщо testedValue == value
     */
    boolean equals(T validatedValue, T value);

    /**
     * Менш ніж чи дорівнює
     * @param validatedValue значення для валідації
     * @param value значення з яким порівнюється
     * @return true якщо testedValue <= value
     */
    boolean isLesserOrEquals(T validatedValue, T value);

    /**
     * Менш ніж
     * @param validatedValue значення для валідації
     * @param value значення з яким порівнюється
     * @return true якщо testedValue < value
     */
    boolean isLesserThan(T validatedValue, T value);

    /**
     * Потрапляє в проміжок
     * @param validatedValue значення для валідації
     * @param fromValue значення З проміжку (включно)
     * @param toValue значення ПО проміжку (включно)
     * @return true якщо fromValue <= testedValue <= toValue
     */
    boolean between(T validatedValue, T fromValue, T toValue);
}

package embasa.validators;

/** Базовий інтерфейс валідатора.  */
public interface BaseValidator<T> {

    /**
     * Превірити чи об'єкт має дані
     * @param validatedValue значення для валідації
     * @return true якщо об'єкт не має даних
     */
    boolean isEmpty(T validatedValue);

    /**
     * Перевірити відповідність регулярному виразу
     * @param validatedValue значення для валідації
     * @param regExp регулярний вираз
     * @return true в разі відповідності об'єкта регулярному виразу
     */
    boolean isMatches(String validatedValue, String regExp);
}

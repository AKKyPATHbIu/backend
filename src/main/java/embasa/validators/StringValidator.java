package embasa.validators;

/** Строковий валідатор. */
public interface StringValidator<T extends String> extends BaseValidator<T> {

    /**
     * Довжина не менша ніж.
     * @param validatedValue значення для валідації
     * @param length мінімальна довжина
     * @return true якщо довжина строки не менша за length
     */
    boolean isLengthNotLesserThan(T validatedValue, int length);

    /**
     * Довжина не більша ніж.
     * @param validatedValue значення для валідації
     * @param length максимальна довжина
     * @return true якщо довжина строки не більша за length
     */
    boolean isLengthNotGreaterThan(T validatedValue, int length);
}

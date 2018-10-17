package embasa.validators;

/** Валідатор дати. */
public interface DateValidator<T extends java.util.Date> extends BaseValidator<T> {

    /**
     * Ознака, що дата поточна
     * @param validatedValue значення для валідації
     * @return true, якщо дата поточна
     */
    boolean isToday(T validatedValue);

    /**
     * Дата не більша за
     * @param validatedValue значення для валідації
     * @param date порівнювана дата
     * @return true, якщо дата не більша за date
     */
    boolean before(T validatedValue, T date);

    /**
     * Дата не більша за
     * @param validatedValue значення для валідації
     * @param date порівнювана дата
     * @return true, якщо дата не більша за date
     */
    boolean beforeWithoutTime(T validatedValue, T date);

    /**
     * Дата не більша за
     * @param validatedValue значення для валідації
     * @param date порівнювана дата
     * @return true, якщо дата не більша за date
     */
    boolean after(T validatedValue, T date);

    /**
     * Дата не більша за
     * @param validatedValue значення для валідації
     * @param date порівнювана дата
     * @return true, якщо дата не більша за date
     */
    boolean afterWithoutTime(T validatedValue, T date);

    /**
     * Дата не більша за поточну, мінус amount днів/місяців/років
     * @param validatedValue значення для валідації
     * @param calendarField тип одиниці (Calendar.DAY_OF_YEAR, Calendar.MONTH, Calendar.YEAR)
     * @param amount кількість одиниць
     * @return true, якщо дата менша за поточну, мінус amount одиниць періода
     */
    boolean beforeTodayMinus(T validatedValue, int calendarField, int amount);
}

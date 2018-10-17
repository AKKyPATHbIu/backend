package embasa.persistence;

/** Допоміжна утиліта для роботи з jdbc. */
public class JdbcUtil {

    /**
     * Екрантувати лапки<br>
     *
     * <br>наприклад на вході : { \"field\" : value }
     * <br>на виході: { \\\"field\\\" : value }
     * <br>
     * @param value срока з лапками
     * @return строка з екранованими лапками
     */
    public static String escapeQuoters(String value) {
        return value.replace("\"", "\\\"");
    }

    /**
     * Отримати строкове представлення об'єкту або порожню строку, якщо об'єкт null
     * @param obj об'єкт, строкове представлення якого необхідно отримати
     * @return строкове представлення об'єкту або порожню строку, якщо об'єкт null
     */
    public static String objToStr(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    /**
     * Отримати строкове представлення об'єкту або 'null', якщо об'єкт null
     * @param obj об'єкт, строкове представлення якого необхідно отримати
     * @return строкове представлення об'єкту або 'null', якщо об'єкт null
     */
    public static String objToSqlStr(Object obj) {
        return obj == null ? "null" : obj.toString();
    }
}

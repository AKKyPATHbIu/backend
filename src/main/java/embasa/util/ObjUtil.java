package embasa.util;

/** Допоміжна утиліта для роботи з об'єктами. */
public class ObjUtil {

    /**
     * Перевірити еквівалентність об'єктів
     * @param obj1 перший із об'єктів
     * @param obj2 другий із об'єктів
     * @return true якщо об'єкти эквівалентні (або обидва null)
     */
    public static boolean equals(Object obj1, Object obj2) {
        return obj1 == null ? obj2 == null : obj1.equals(obj2);
    }
}

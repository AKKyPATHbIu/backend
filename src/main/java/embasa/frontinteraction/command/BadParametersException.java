package embasa.frontinteraction.command;

/** Виключення помилкових параметрів команди з фронтенда. */
public class BadParametersException extends RuntimeException {

    /**
     * Конструктор
     * @param s тект помилки
     */
    public BadParametersException(String s) {
        super(s);
    }
}

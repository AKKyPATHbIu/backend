package embasa.connection;

import java.util.Properties;

/**
 * Зчитування налаштувань конекта для hibernate з property-файла
 */
public interface ConnectionPropertyLoaderFromFile {

    /**
     * Створити об'єкт Property з кофігурацією конекта для hibernate з property-файла
     * @param fileName им'я файлу з даними для створення налаштувань конекту до баз даних
     * @return об'єкт Property з кофігурацією конекта для hibernate з property-файла
     */
    Properties createFromFile(String fileName);
}

package embasa.frontinteraction.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import embasa.frontinteraction.Request;
import embasa.persistence.securedb.model.User;

import java.util.Locale;

/** Команда. */
public interface Command {

    /** Мапер для перетворювань об'єкта з/в json. */
    ObjectMapper mapper = new ObjectMapper();

    /**
     * Отримати ім'я команди.
     * @return им'я команди
     */
    String getName();

    /**
     * Виконати команду з перевіркою параметрів.
     * @param request запит з frontend-у
     * @param user поточний користувач
     * @return результат запиту у вигляді json
     * throws BadParametersException
     */
    String execute(Request request, User user) throws BadParametersException, JsonProcessingException;

    /**
     * Виконати команду
     * @param request запит з frontend-у
     * @param user поточний користувач
     * @return результат запиту у вигляді json
     * throws BadParametersException
     */
    String executeCmd(Request request, User user) throws BadParametersException, JsonProcessingException;

    /**
     * Перевірити параметри.
     * @param params параметри, які необхідно перевірити
     * @param locale локаль
     * throws BadParametersException
     */
    void checkParams(String params, Locale locale) throws BadParametersException;

    /**
     * Отримати сигнатуру виклику метода з переліком типів параметрів
     * @return сигнура виклику метода з переліком типів параметрів
     */
    String getSignature();
}

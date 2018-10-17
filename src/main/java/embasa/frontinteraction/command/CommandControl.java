package embasa.frontinteraction.command;

import embasa.frontinteraction.Request;
import embasa.persistence.securedb.model.User;

/** Виконувач відповідної команди. */
public interface CommandControl {

    /**
     * Виконати відповідну команду
     * @param request запит
     * @param user поточний користувач
     * @return результат команди
     */
    String proceed(Request request, User user) throws Exception;
}

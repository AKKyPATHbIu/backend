package embasa.frontinteraction.command;

import embasa.frontinteraction.Request;
import embasa.frontinteraction.Response;
import embasa.persistence.securedb.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/** Виконувач команд. */
public class CommandControlImpl implements CommandControl {

    /** Перелік доступних команд. */
    private final Command[] commands;

    /**
     * Конструктор
     * @param commandHolder утримувач команд
     */
    public CommandControlImpl(CommandHolder commandHolder) {
        commands = commandHolder.getAvailableCommands();
    }

    @Override
    public String proceed(Request request, User user) throws Exception {
        String method = request.getRequest();
        for (Command cmd : commands) {
            if (cmd.getName().equalsIgnoreCase(method)) {
                return cmd.execute(request, user);
            }
        }
        return String.format(Response.TEMPLATE, request.getEvent(), Response.UNKNOWN_METHOD);
    }
}

package embasa.frontinteraction.command.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import embasa.frontinteraction.Request;
import embasa.frontinteraction.Response;
import embasa.frontinteraction.command.BadParametersException;
import embasa.frontinteraction.command.Command;
import embasa.frontinteraction.command.CommandHolder;
import embasa.persistence.securedb.model.User;
import org.springframework.context.ApplicationContext;

/** Отримати всі доступні запити по WebSocket-у. */
public class GetAvailableRequests extends BaseCommandWithoutParamsImpl implements Command {

    /** Контекст додатку. */
    private static ApplicationContext appContext;

    /** Утримувач всіх запитів. */
    private CommandHolder commandHolder;

    /** Ім'я біна утримувача команд. */
    private final String commandHolderBeanName;

    /**
     * Конструктор
     * @param appContext контекст додатку
     * @param commandHolderBeanName ім'я біна утримувача команд
     */
    public GetAvailableRequests(ApplicationContext appContext, String commandHolderBeanName) {
        this.appContext = appContext;
        this.commandHolderBeanName = commandHolderBeanName;
    }

    @Override
    public String getName() {
        return "getAvailableRequests";
    }

    @Override
    public String executeCmd(Request request, User user) throws BadParametersException, JsonProcessingException {
        Response<String[]> response = new Response<> ();
        response.setEvent(request.getEvent()).setStatus(Response.OK).setData(getAllRequests());
        return Command.mapper.writeValueAsString(response);
    }

    /**
     * Отримати перелік сигнатур всіх доступних запистів
     * @return перелік сигнатур всіх доступних запистів
     */
    private String[] getAllRequests() {
        CommandHolder cmdHolder = getCommandHolder();
        Command[] availCmds = cmdHolder.getAvailableCommands();
        String[] result = new String[availCmds.length];
        for (int i = 0; i < availCmds.length; i++) {
            result[i] = availCmds[i].getSignature();
        }
        return result;
    }

    /**
     * Отримати утримувача запитів
     * @return утримувач запитів
     */
    private CommandHolder getCommandHolder() {
        if (commandHolder == null) {
            commandHolder = (CommandHolder) appContext.getBean(commandHolderBeanName);
        }
        return commandHolder;
    }
}

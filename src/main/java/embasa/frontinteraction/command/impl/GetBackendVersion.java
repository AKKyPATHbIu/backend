package embasa.frontinteraction.command.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import embasa.frontinteraction.Request;
import embasa.frontinteraction.Response;
import embasa.frontinteraction.command.BadParametersException;
import embasa.frontinteraction.command.Command;
import embasa.persistence.securedb.model.User;
import embasa.util.PropertiesHolder;
import org.springframework.beans.factory.annotation.Autowired;

/** Команда отримання версії backend-у. */
public class GetBackendVersion extends BaseCommandWithoutParamsImpl implements Command {

    /** Версія backend-у. */
    private String version;

    @Autowired
    public void setPropertiesHolder(PropertiesHolder propsHolder) {
        version = propsHolder.get(PropertiesHolder.VERSION);
    }

    @Override
    public String getName() {
        return "getBackendVersion";
    }

    @Override
    public String executeCmd(Request request, User user) throws BadParametersException, JsonProcessingException {
        Response<String> response = new Response<> ();
        response.setEvent(request.getEvent());
        response.setStatus(Response.OK);
        response.setData(version);
        return Command.mapper.writeValueAsString(response);
    }
}

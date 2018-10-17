package embasa.frontinteraction.command.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import embasa.frontinteraction.Request;
import embasa.frontinteraction.Response;
import embasa.frontinteraction.command.BadParametersException;
import embasa.frontinteraction.command.Command;
import embasa.persistence.common.model.Language;
import embasa.persistence.common.service.LanguageService;
import embasa.persistence.securedb.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;

/** Отримання переліку підтримуваних системою мов. */
public class GetAvailableLanguages extends BaseCommandWithoutParamsImpl implements Command {

    /** Сервіс мови інтерфейсу. */
    public LanguageService languageService;

    @Autowired
    public void setLanguageService(@Qualifier(value = "secureDBLanguageService") LanguageService languageService) {
        this.languageService = languageService;
    }

    @Override
    public String getName() {
        return "getAvailableLanguages";
    }

    @Override
    public String executeCmd(Request request, User user) throws BadParametersException, JsonProcessingException {
        ArrayList<Language> languages = new ArrayList<> (languageService.findAll());
        Response<ArrayList<Language>> response = new Response<> ();
        response.setEvent(request.getEvent());
        response.setStatus(Response.OK);
        response.setData(languages);
        return Command.mapper.writeValueAsString(response);
    }
}

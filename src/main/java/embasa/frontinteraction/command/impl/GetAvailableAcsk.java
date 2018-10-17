package embasa.frontinteraction.command.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import embasa.frontinteraction.Request;
import embasa.frontinteraction.Response;
import embasa.frontinteraction.command.BadParametersException;
import embasa.frontinteraction.command.Command;
import embasa.persistence.common.model.Language;
import embasa.persistence.securedb.model.Acsk;
import embasa.persistence.securedb.model.User;
import embasa.persistence.securedb.service.AcskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.List;

/** Запит на отримання переліку доступних АЦСК. */
public class GetAvailableAcsk extends BaseCommandWithoutParamsImpl implements Command {

    /** Сервіс АЦСК. */
    private AcskService acskService;

    @Autowired
    public void setAcskService(@Qualifier(value = "secureDBAcskService") AcskService acskService) {
        this.acskService = acskService;
    }

    @Override
    public String getName() {
        return "getAvailableAcsk";
    }

    @Override
    public String executeCmd(Request request, User user) throws BadParametersException, JsonProcessingException {
        Response<embasa.frontinteraction.model.Acsk[]> response = new Response<> ();
        response.setEvent(request.getEvent());
        response.setStatus(Response.OK);
        response.setData(getAvailableAcsk(request.getLanguage()));
        return Command.mapper.writeValueAsString(response);
    }

    /**
     * Отримати перелік доступних АЦСК
     * @return перелік доступних АЦСК
     */
    public embasa.frontinteraction.model.Acsk[] getAvailableAcsk(Language lang) {
        List<embasa.frontinteraction.model.Acsk> result = new ArrayList<>();
        List<Acsk> allAcsk = acskService.findAll();
        for (int i = 0; i < allAcsk.size(); i++) {
            Acsk acsk = allAcsk.get(i);
            embasa.frontinteraction.model.Acsk respAcsk = new embasa.frontinteraction.model.Acsk();
            respAcsk.setCode(acsk.getId().toString());
            respAcsk.setDescription(acsk.getName(lang));
            result.add(respAcsk);
        }
        return result.toArray(new embasa.frontinteraction.model.Acsk[result.size()]);
    }
}

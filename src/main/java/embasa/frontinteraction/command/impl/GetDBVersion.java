package embasa.frontinteraction.command.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import embasa.frontinteraction.Request;
import embasa.frontinteraction.Response;
import embasa.frontinteraction.command.BadParametersException;
import embasa.frontinteraction.command.Command;
import embasa.persistence.securedb.model.DBVersion;
import embasa.persistence.securedb.model.User;
import embasa.persistence.securedb.service.DBVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/** Команда отримання версії бази даних. */
public class GetDBVersion extends BaseCommandWithoutParamsImpl implements Command {

    /** Не знайдено жодного запису!. */
    private static String NO_RECORD_FOUND_KEY = "noRecordFound";

    /** Сервіс версій змін. */
    private DBVersionService DBVersionService;

    @Autowired
    @Qualifier(value = "secureDBDBVersionService")
    public void setDBVersionService(DBVersionService DBVersionService) {
        this.DBVersionService = DBVersionService;
    }

    @Override
    public String getName() {
        return "getDBVersion";
    }

    @Override
    public String executeCmd(Request request, User user) throws BadParametersException, JsonProcessingException {
        DBVersion version = DBVersionService.findLastByApplyDate();
        if (version == null) {
            String errMessage = localizer.getMessage(NO_RECORD_FOUND_KEY, request.getLanguage().getLocale());
            return String.format(Response.TEMPLATE_WITH_DATA, request.getEvent(),
                    Response.UNSUCCESS, errMessage);
        }

        Response<DBVersion> response = new Response<> ();
        response.setEvent(request.getEvent());
        response.setStatus(Response.OK);
        response.setData(version);

        return mapper.writeValueAsString(response);
    }
}

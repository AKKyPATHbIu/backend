package embasa.frontinteraction.command.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import embasa.frontinteraction.Request;
import embasa.frontinteraction.Response;
import embasa.frontinteraction.command.BadParametersException;
import embasa.frontinteraction.command.Command;
import embasa.frontinteraction.model.User;

import java.util.Date;

/** Команда отримання інформації про поточного користувача. */
public class GetCurrentUserInfo extends BaseCommandWithoutParamsImpl implements Command {

    @Override
    public String getName() {
        return "getCurrentUserInfo";
    }

    @Override
    public String executeCmd(Request request, embasa.persistence.securedb.model.User user)
            throws BadParametersException, JsonProcessingException {

        Response<User> response = getCurrentUserInfo(request, user);
        return mapper.writeValueAsString(response);
    }

    /**
     * Отримати інформацію про поточного користувача
     * @param request параметри запросу
     * @param user поточний користувач
     * @return інформацію про поточного користувача
     */
    public Response<User> getCurrentUserInfo(Request request, embasa.persistence.securedb.model.User user) {
        User u = new User();
        u.setId(user.getId()).setName(user.getName()).setEmail(user.getEmail()).setComment(user.getComment());
        Date curDate = new Date();
        Date expDate = user.getPasswordExpiration();
        Boolean isExpired = expDate != null && curDate.compareTo(expDate) >= 0;
        u.setIsPasswordExpired(isExpired);
        Response<User> resp = new Response<> ();
        resp.setStatus(Response.OK).setEvent(request.getEvent()).setData(u);
        return resp;
    }
}

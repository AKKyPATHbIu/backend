package embasa.frontinteraction.command.impl;

import embasa.frontinteraction.Request;
import embasa.frontinteraction.Response;
import embasa.frontinteraction.command.Command;
import embasa.frontinteraction.command.params.ChangePaswordParam;
import embasa.persistence.securedb.model.User;
import embasa.persistence.securedb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;

/** Команда зміни паролю користувача. */
public class ChangePassword extends BaseCommandWithoutParamsImpl implements Command {

    /** Невірний поточний пароль! */
    private static String BAD_CURRENT_PASSWORD_KEY = "badCurrentPassword";

    /** Новий пароль має відрізнятися від поточного! */
    private static String NEW_PASSWORD_SHOULD_DIFFER_KEY = "newPasswordShouldDiffer";

    /** Декодер паролю. */
    private PasswordEncoder passwordEncoder;

    /** Сервіс користувача. */
    private UserService userService;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    @Qualifier(value = "secureDBUserService")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /** Конструктор за замовчуванням. */
    public ChangePassword() {
        super(new Class[] { String.class, String.class });
    }

    @Override
    public String getName() {
        return "changePassword";
    }

    @Override
    public String executeCmd(Request request, User user) {
        String UNSUCCESS_RESPONSE = String.format(Response.TEMPLATE_WITH_DATA, request.getEvent(), Response.UNSUCCESS, "%s");

        try {
            ChangePaswordParam params = Command.mapper.readValue(request.getTransferData(), ChangePaswordParam.class);

            if (!passwordEncoder.matches(params.getOldPassword(), user.getPassword())) {
                return String.format(UNSUCCESS_RESPONSE,
                        localizer.getMessage(BAD_CURRENT_PASSWORD_KEY, request.getLanguage().getLocale()));
            }

            if (params.getOldPassword().equals(params.getNewPassword())) {
                return String.format(UNSUCCESS_RESPONSE,
                        localizer.getMessage(NEW_PASSWORD_SHOULD_DIFFER_KEY, request.getLanguage().getLocale()));
            }

            user.setPassword(passwordEncoder.encode(params.getNewPassword()));
            user.setPasswordExpiration(null);
            userService.update(user);
            return String.format(Response.TEMPLATE, request.getEvent(), Response.OK);

        } catch (Exception ex) {
            return String.format(UNSUCCESS_RESPONSE,
                    localizer.getMessage(BAD_PARAM, request.getLanguage().getLocale()));
        }
    }
}

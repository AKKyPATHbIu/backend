package embasa.frontinteraction.command.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import embasa.frontinteraction.Request;
import embasa.frontinteraction.Response;
import embasa.frontinteraction.command.BadParametersException;
import embasa.frontinteraction.command.Command;
import embasa.i18n.Localizer;
import embasa.persistence.securedb.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Locale;

/** Базовий класс команди без параметрів. */
public abstract class BaseCommandWithoutParamsImpl implements Command {

    /** Невірний тип параметрів! */
    protected static String BAD_PARAM = "badParam";

    /** Локалізатор. */
    protected Localizer localizer;

    @Autowired
    public void setLocalizer(Localizer localizer) {
        this.localizer = localizer;
    }

    /** Перелік типів параметрів. */
    private Class[] paramsTypes;

    /** Конструктор за замовчуванням. */
    public BaseCommandWithoutParamsImpl() {
        this(new Class[] {});
    }

    /**
     * Конструктор
     * @param params перелік типів параметрів
     */
    public BaseCommandWithoutParamsImpl(Class[] params) {
        this.paramsTypes = params;
    }

    @Override
    public String execute(Request request, User user) throws BadParametersException, JsonProcessingException {
        try {
            checkParams(request.getTransferData(), request.getLanguage().getLocale());
        } catch (BadParametersException ex) {
            return String.format(Response.TEMPLATE_WITH_DATA, request.getEvent(), Response.UNSUCCESS, ex.getMessage());
        }
        return executeCmd(request, user);
    }

    @Override
    public void checkParams(String params, Locale locale) {

    }

    @Override
    public String getSignature() {
        StringBuilder result = new StringBuilder();
        result.append(getName());
        if (paramsTypes.length > 0) {
            result.append("(").append(paramsTypes[0].getSimpleName());
            for (int i = 1; i < paramsTypes.length; i++) {
                result.append(", ").append(paramsTypes[i].getSimpleName());
            }
            result.append(")");
        }
        return result.toString();
    }
}

package embasa.handlers;

import com.fasterxml.jackson.databind.InjectableValues;
import embasa.frontinteraction.Request;
import embasa.frontinteraction.Response;
import embasa.frontinteraction.command.Command;
import embasa.frontinteraction.command.CommandControl;
import embasa.i18n.LanguageHolder;
import embasa.persistence.securedb.model.User;
import embasa.persistence.securedb.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;

/** Обробник повідомлень закритого каналу. */
public class MessageHandlerEmbas extends TextWebSocketHandler {

    private static Logger logger = Logger.getLogger(TextWebSocketHandler.class);

    /** Виконувач команд. */
    private CommandControl cmdControl;

    /** Сервіс користувача. */
    private UserService userService;

    /** Утримувач всіх мов системи. */
    private LanguageHolder languageHolder;

    User unonimous;

    public MessageHandlerEmbas() {
        unonimous = new User();
        unonimous.setName("unonimous");
        unonimous.setId(-1L);
    }

    @Autowired
    @Qualifier(value = "commandControlEmbas")
    public void setCommandControl(CommandControl cmdControl) {
        this.cmdControl = cmdControl;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    @Qualifier(value = "mainDBLanguageHolder")
    public void setLanguageHolder(LanguageHolder languageHolder) {
        this.languageHolder = languageHolder;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("Соединение установлено! Пользователь: " + getUser(session).getName());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        User user = getUser(session);
        String payload = message.getPayload();
        logger.info("Получено сообщение от пользователя: " + user.getName() + ", сообщение: " + payload);
        try {
            if (Command.mapper.getInjectableValues() == null) {
                final InjectableValues.Std injectableValues = new InjectableValues.Std();
                injectableValues.addValue(LanguageHolder.class, languageHolder);
                Command.mapper.setInjectableValues(injectableValues);
            }

            Request request = Command.mapper.readValue(payload, Request.class);
            String response = cmdControl.proceed(request, user);
            session.sendMessage(new TextMessage(response));
        } catch (Exception ex) {
            logger.error(payload);
            logger.error(ex);
            ex.printStackTrace();
            session.sendMessage(new TextMessage(String.format(Response.TEMPLATE, "unknown", Response.BAD_REQUEST)));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.info("Соединение закрыто! Пользователь: " + getUser(session).getName());
    }

    private User getUser(WebSocketSession session) {
        Map<String, Object> handshakeAttributes = session.getAttributes();
        SecurityContext context = (SecurityContext) handshakeAttributes
                .get("SPRING_SECURITY_CONTEXT");
        if (context == null) {
            return unonimous;
        }
        Authentication authentication = context.getAuthentication();
        Long id = (Long) authentication.getPrincipal();
        return userService.findById(id);
    }
}

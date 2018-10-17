package embasa.handlers;

import com.fasterxml.jackson.databind.InjectableValues;
import embasa.frontinteraction.Request;
import embasa.frontinteraction.Response;
import embasa.frontinteraction.command.Command;
import embasa.frontinteraction.command.CommandControl;
import embasa.i18n.LanguageHolder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/** Обробник повідомлень відкритого каналу. */
public class MessageHandlerGeneral extends TextWebSocketHandler {

    private static Logger logger = Logger.getLogger(MessageHandlerGeneral.class);

    /** Виконувач команд. */
    private CommandControl cmdControl;

    /** Утримувач всіх мов системи. */
    private LanguageHolder languageHolder;

    @Autowired
    @Qualifier(value = "commandControlGeneral")
    public void setCommandControl(CommandControl cmdControl) {
        this.cmdControl = cmdControl;
    }

    @Autowired
    @Qualifier(value = "secureDBLanguageHolder")
    public void setLanguageHolder(LanguageHolder languageHolder) {
        this.languageHolder = languageHolder;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("general: Соединение установлено!");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        logger.info("general: Получено сообщение: " + payload);
        try {
            if (Command.mapper.getInjectableValues() == null) {
                final InjectableValues.Std injectableValues = new InjectableValues.Std();
                injectableValues.addValue(LanguageHolder.class, languageHolder);
                Command.mapper.setInjectableValues(injectableValues);
            }

            Request request = Command.mapper.readValue(payload, Request.class);
            String response = cmdControl.proceed(request, null);
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
        logger.info("general: Соединение закрыто!");
    }
}

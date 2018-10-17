package embasa.controllers;

import com.github.messenger4j.Messenger;
import com.github.messenger4j.exception.MessengerApiException;
import com.github.messenger4j.exception.MessengerIOException;
import com.github.messenger4j.exception.MessengerVerificationException;
import com.github.messenger4j.send.MessagePayload;
import com.github.messenger4j.send.MessageTag;
import com.github.messenger4j.send.MessagingType;
import com.github.messenger4j.send.message.TextMessage;
import com.github.messenger4j.send.recipient.IdRecipient;
import com.github.messenger4j.userprofile.UserProfile;
import com.github.messenger4j.webhook.event.TextMessageEvent;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.time.Instant;
import java.util.Optional;

import static java.util.Optional.of;

@RestController
@RequestMapping(value = "/webhook")
public class WebhookController {

    private static Logger logger = Logger.getLogger(WebhookController.class);

    private final String APP_SECRET = "56adea26c88507d5831bb37c96d46f8d";
    private final String VERIFY_TOKEN = "S3CRET";
    private final String PAGE_ACCESS_TOKEN = "EAAeUqiBCw9EBAG4DdKhykhCWS2aTJZAm2deKEiNDz9507AxTVrhxm9sJnDcG6ovSzojvE2vSDP6uEFM2E1eZCJ0jpbEKZBST0tPENrdlQcpuDjBFHZAm2xONyT6dCz2ese2df9AxnFEd2WkvZCQlnetld5KTIV6C8qZBn2izitagZDZD";

    private final Messenger messenger;

    public WebhookController() {
        messenger = Messenger.create(PAGE_ACCESS_TOKEN, APP_SECRET, VERIFY_TOKEN);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity verifyWebhook(@RequestParam("hub.mode") final String mode,
                                        @RequestParam("hub.verify_token") final String verifyToken,
                                        @RequestParam("hub.challenge") final String challenge) {
        if ("subscribe".equalsIgnoreCase(mode)) {
            if (VERIFY_TOKEN.equals(verifyToken)) {
                logger.info("subscibed");
                return ResponseEntity.ok(challenge);
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Something went wrong!");
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> handleCallback(@RequestBody final String payload,
                                               @RequestHeader("X-Hub-Signature") final String signature) {
        logger.info(String.format("Received a message! %s", payload));
        try {
            messenger.onReceiveEvents(payload, of(signature), event -> {
                if (event.isTextMessageEvent()) {
                    handleTextMessageEvent(event.asTextMessageEvent());
                }
            });
        } catch (MessengerVerificationException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Опрацювати подію надходження текстового повідомлення
     * @param event параметри події
     */
    private void handleTextMessageEvent(TextMessageEvent event) {
        logger.info(String.format("Received TextMessageEvent: %s", event));

        final String messageId = event.messageId();
        final String messageText = event.text();
        final String senderId = event.senderId();
        final Instant timestamp = event.timestamp();

        logger.info(String.format("Received message %s with text %s from user %s at %s", messageId, messageText, senderId, timestamp));

        String name = "sender";

        try {
            UserProfile up = messenger.queryUserProfile(senderId);
            name = up.firstName() + " " + up.lastName();
        } catch (Exception ex) {
            handleSendException(ex);
        }

        try {
            switch (messageText.toLowerCase()) {
                case "hello":
                    sendTextMessage(senderId, String.format("Hello %s with id = %s", name, senderId));
                    break;

                default:
                    sendTextMessage(senderId, messageText);
            }
        } catch (MessengerApiException | MessengerIOException | MalformedURLException e) {
            handleSendException(e);
        }
    }

    /**
     * Надіслати повідомлення
     * @param recipientId psid отримувача
     * @param text текст повідомлення
     * @throws MessengerApiException
     * @throws MessengerIOException
     * @throws MalformedURLException
     */
    private void sendTextMessage(String recipientId, String text) throws MessengerApiException, MessengerIOException, MalformedURLException {
        try {
            final MessagePayload messagePayload = MessagePayload.create(IdRecipient.create(recipientId),
                    MessagingType.MESSAGE_TAG, TextMessage.create(text), Optional.empty(),
                    Optional.of(MessageTag.COMMUNITY_ALERT));

            this.messenger.send(messagePayload);
        } catch (MessengerApiException | MessengerIOException e) {
            handleSendException(e);
        }
    }

    /**
     * Обробити виключення
     * @param e виключення, яке необхідно обробити
     */
    private void handleSendException(Exception e) {
        logger.error("Message could not be sent. An unexpected error occurred.", e);
    }
}


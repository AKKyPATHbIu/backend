package embasa.frontinteraction.command.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import embasa.LangUtil;
import embasa.frontinteraction.Request;
import embasa.frontinteraction.Response;
import embasa.frontinteraction.command.Command;
import embasa.i18n.LanguageHolder;
import embasa.i18n.LocaleUtil;
import embasa.i18n.Localizer;
import embasa.persistence.securedb.model.User;
import embasa.persistence.securedb.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ChangePasswordTest {

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    UserService userService;

    @Mock
    Localizer localizer;

    @Mock
    LanguageHolder languageHolder;

    @InjectMocks
    Command changePassword = new ChangePassword();

    @Test
    public void getName() {
        assertEquals(changePassword.getName(), "changePassword");
    }

    @Test
    public void execute() {
        when(localizer.getMessage("badCurrentPassword", LocaleUtil.LOCALE_UA)).thenReturn("Невірний поточний пароль!");
        when(localizer.getMessage("newPasswordShouldDiffer", LocaleUtil.LOCALE_UA)).thenReturn("Новий пароль має відрізнятися від поточного!");
        String oldPassword = "old_pass";
        String newPassword = "new_pass";
        String badPassword = "123";

        User user = new User();
        user.setId(10L);
        user.setPassword(oldPassword);

        when(passwordEncoder.matches(oldPassword, oldPassword)).thenReturn(true);

        when(languageHolder.getLanguageBy("ua")).thenReturn(LangUtil.LANG_UA);

        Request request = new Request(languageHolder);
        request.setLang("ua");
        request.setRequest(changePassword.getName());
        request.setEvent("changingPassword");
        request.setClientVersion("0.0.1");
        request.setTransferData(String.format("{ \"oldPassword\":\"%s\", \"newPassword\":\"%s\" }", oldPassword, newPassword));

        try {
            String result = changePassword.execute(request, user);
            assertEquals(String.format(Response.TEMPLATE, request.getEvent(), Response.OK), result);
            verify(userService, times(1)).update(user);
        } catch (Exception ex) {
            fail();
        }

        when(passwordEncoder.matches(oldPassword, oldPassword)).thenReturn(true);
        request.setTransferData(String.format("{ \"oldPassword\":\"%s\", \"newPassword\":\"%s\" }", oldPassword, oldPassword));
        user.setPassword(oldPassword);
        try {
            String result = changePassword.execute(request, user);
            String expectedString = String.format(Response.TEMPLATE_WITH_DATA,
                    request.getEvent(), Response.UNSUCCESS, "Новий пароль має відрізнятися від поточного!");
            assertEquals(expectedString, result);
        } catch (JsonProcessingException ex) {
            fail();
        }

        when(passwordEncoder.matches(oldPassword, badPassword)).thenReturn(false);
        user.setPassword(badPassword);

        try {
            String result = changePassword.execute(request, user);
            assertEquals(String.format(Response.TEMPLATE_WITH_DATA, request.getEvent(),
                    Response.UNSUCCESS, "Невірний поточний пароль!"), result);
        } catch (JsonProcessingException ex) {
            fail();
        }
    }
}

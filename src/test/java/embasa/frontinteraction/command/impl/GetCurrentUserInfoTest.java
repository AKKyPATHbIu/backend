package embasa.frontinteraction.command.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import embasa.LangUtil;
import embasa.frontinteraction.Request;
import embasa.frontinteraction.Response;
import embasa.frontinteraction.command.Command;
import embasa.i18n.LanguageHolder;
import embasa.persistence.securedb.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetCurrentUserInfoTest {

    Command cmd = new GetCurrentUserInfo();

    @Mock
    LanguageHolder languageHolder;

    @Test
    public void getName() {
        assertEquals("getCurrentUserInfo", cmd.getName());
    }

    @Test
    public void execute() {
        when(languageHolder.getLanguageBy("ua")).thenReturn(LangUtil.LANG_UA);

        Request request = new Request(languageHolder);
        request.setClientVersion("0.0.1");
        request.setLang("ua");
        request.setEvent("event");
        request.setRequest("getCurrentUserInfo");

        User user = new User();
        user.setId(20L);
        user.setName("test_user");
        user.setEmail("test_user@gmail.com");
        user.setComment("test_user comments");

        try {
            String result = cmd.execute(request, user);
            Response<embasa.frontinteraction.model.User> resp = Command.mapper.readValue(result,
                    new TypeReference<Response<embasa.frontinteraction.model.User>>() {});

            assertEquals(Response.OK, resp.getStatus());
            assertEquals(request.getEvent(), resp.getEvent());

            embasa.frontinteraction.model.User respUser = resp.getData();
            assertEquals(user.getId(), respUser.getId());
            assertEquals(user.getName(), respUser.getName());
            assertEquals(user.getEmail(), respUser.getEmail());
            assertEquals(user.getComment(), respUser.getComment());
        } catch (Exception ex) {
            fail();
        }
    }
}
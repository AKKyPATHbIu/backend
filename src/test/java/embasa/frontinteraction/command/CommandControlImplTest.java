package embasa.frontinteraction.command;

import embasa.LangUtil;
import embasa.config.RootConfig;
import embasa.frontinteraction.Request;
import embasa.frontinteraction.Response;
import embasa.frontinteraction.command.impl.GetCurrentUserInfo;
import embasa.i18n.LanguageHolder;
import embasa.persistence.common.model.Language;
import embasa.persistence.securedb.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class,
        classes = { RootConfig.class })
@ActiveProfiles("DEV")
public class CommandControlImplTest {

    @Autowired
    @Qualifier(value = "commandControlEmbas")
    CommandControl cmdControl;

    @Test
    public void proceed() throws Exception {
        Request req = new Request(code -> {
            if ("ua".equalsIgnoreCase(code)) {
                return LangUtil.LANG_UA;
            }
            return null;
        });

        req.setClientVersion("0.0.1");
        req.setEvent("event");
        req.setLang("ua");
        req.setRequest("getCurrentUserInfo");

        User user = new User();
        user.setId(20L);
        user.setName("test_user");
        user.setEmail("test_user@gmail.com");
        user.setComment("test_user comments");

        String ctrlResult = cmdControl.proceed(req, user);
        Command cmd = new GetCurrentUserInfo();
        String cmdResult = cmd.execute(req, user);
        assertNotNull(ctrlResult);
        assertEquals(ctrlResult, cmdResult);

        req.setRequest("unknownRequest");

        ctrlResult = cmdControl.proceed(req, user);
        assertEquals(String.format(Response.TEMPLATE, req.getEvent(), Response.UNKNOWN_METHOD), ctrlResult);
    }
}
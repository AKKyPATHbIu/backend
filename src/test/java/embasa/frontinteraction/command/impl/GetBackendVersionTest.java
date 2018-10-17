package embasa.frontinteraction.command.impl;

import embasa.LangUtil;
import embasa.frontinteraction.Request;
import embasa.i18n.LanguageHolder;
import embasa.util.PropertiesHolder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetBackendVersionTest {

    @Mock
    PropertiesHolder propsHolder;

    GetBackendVersion cmdGetBackendVersion = new GetBackendVersion();

    @Mock
    LanguageHolder languageHolder;

    /** Версія для тестування*/
    private final String TEST_VERSION = "10.20.49";

    /** Тип події. */
    private final String EVENT = "test";

    /** Очікувана відповідь. */
    private final String EXPECTED_RESPONSE = String.format("{\"status\":200,\"event\":\"%s\",\"data\":\"%s\"}",
            EVENT, TEST_VERSION);

    @Before
    public void init() {
        when(propsHolder.get(PropertiesHolder.VERSION)).thenReturn(TEST_VERSION);
        when(languageHolder.getLanguageBy(LangUtil.LANG_UA.getLangCode())).thenReturn(LangUtil.LANG_UA);
        cmdGetBackendVersion.setPropertiesHolder(propsHolder);
    }

    @Test
    public void testGetName() {
        assertEquals("getBackendVersion", cmdGetBackendVersion.getName());
    }

    @Test
    public void testExecute() {
        Request req = new Request(languageHolder);
        req.setClientVersion("0.0.1");
        req.setLang(LangUtil.LANG_UA.getLangCode());
        req.setEvent(EVENT);
        try {
            String cmdResponse = cmdGetBackendVersion.execute(req, null);
            assertEquals(EXPECTED_RESPONSE, cmdResponse);
        } catch (Exception ex) {
            ex.printStackTrace();
            fail();
        }
    }
}
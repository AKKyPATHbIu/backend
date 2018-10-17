package embasa.frontinteraction.command.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import embasa.LangUtil;
import embasa.frontinteraction.Request;
import embasa.frontinteraction.Response;
import embasa.frontinteraction.command.Command;
import embasa.i18n.LanguageHolder;
import embasa.persistence.common.model.Language;
import embasa.persistence.common.service.LanguageService;
import embasa.persistence.securedb.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetAvailableLanguagesTest {

    @Mock
    LanguageService langService;

    @Mock
    LanguageHolder languageHolder;

    @InjectMocks
    private Command getAvailableLanguages = new GetAvailableLanguages();

    @Test
    public void getName() {
        assertEquals("getAvailableLanguages", getAvailableLanguages.getName());
    }

    @Test
    public void executeCmd() {
        when(languageHolder.getLanguageBy("ua")).thenReturn(LangUtil.LANG_UA);

        Request request = new Request(languageHolder);
        request.setRequest(getAvailableLanguages.getName());
        request.setEvent("getLanguage");

        ArrayList<Language> languages = getAllLanguages();

        when(langService.findAll()).thenReturn(languages);

        try {
            String result = getAvailableLanguages.executeCmd(request, new User());
            Response<ArrayList<Language>> response = Command.mapper.readValue(result,
                    new TypeReference<Response<ArrayList<Language>>>() {});
            assertEquals(request.getEvent(), response.getEvent());
            assertEquals(Response.OK, response.getStatus());

            ArrayList<Language> resp = response.getData();
            Assert.assertTrue(languages.containsAll(resp) && languages.size() == resp.size());
        } catch (Exception ex) {
            fail();
        }
    }

    /**
     * Отримати перелік мов
     * @return перелік мов
     */
    private ArrayList<Language> getAllLanguages() {
        ArrayList<Language> languages = new ArrayList<> ();
        languages.add(LangUtil.LANG_UA);
        languages.add(LangUtil.LANG_RU);
        languages.add(LangUtil.LANG_EN);
        return languages;
    }
}
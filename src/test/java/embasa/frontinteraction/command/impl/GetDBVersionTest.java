package embasa.frontinteraction.command.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import embasa.LangUtil;
import embasa.frontinteraction.Request;
import embasa.frontinteraction.Response;
import embasa.frontinteraction.command.Command;
import embasa.i18n.LanguageHolder;
import embasa.i18n.LocaleUtil;
import embasa.i18n.Localizer;
import embasa.persistence.securedb.model.DBVersion;
import embasa.persistence.securedb.model.User;
import embasa.persistence.securedb.service.DBVersionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetDBVersionTest {

    @Mock
    DBVersionService DBVersionService;

    @Mock
    Localizer localizer;

    @Mock
    LanguageHolder languageHolder;

    @InjectMocks
    Command getDBVersion = new GetDBVersion();

    @Test
    public void getName() {
        assertEquals( getDBVersion.getName(), "getDBVersion");
    }

    @Test
    public void execute() {
        DBVersion DBVersion = new DBVersion();
        DBVersion.setApplyDate(new Date());
        DBVersion.setVersion("1.0.1");
        DBVersion.setDescription("description");

        when(DBVersionService.findLastByApplyDate()).thenReturn(DBVersion);

        when(languageHolder.getLanguageBy("ua")).thenReturn(LangUtil.LANG_UA);

        Request request = new Request(languageHolder).setEvent("getDBVersion").setLang("ua");
        try {
            String result = getDBVersion.execute(request, new User());

            Response<DBVersion> response = new Response<> ();
            response.setStatus(Response.OK);
            response.setEvent(request.getEvent());
            response.setData(DBVersion);

            String expectedString = Command.mapper.writeValueAsString(response);
            assertEquals(expectedString, result);
        } catch (JsonProcessingException ex) {
            fail();
        }
        when(localizer.getMessage("noRecordFound", LocaleUtil.LOCALE_UA)).thenReturn("Не знайдено жодного запису!");
        when(DBVersionService.findLastByApplyDate()).thenReturn(null);
        try {
            String result = getDBVersion.execute(request, new User());
            String expectedString = String.format(Response.TEMPLATE_WITH_DATA, request.getEvent(),
                    Response.UNSUCCESS, "Не знайдено жодного запису!");
            assertEquals(expectedString, result);
        } catch (JsonProcessingException ex) {
            fail();
        }
    }
}
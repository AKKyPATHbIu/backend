package embasa.frontinteraction.command.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import embasa.LangUtil;
import embasa.frontinteraction.Request;
import embasa.frontinteraction.Response;
import embasa.frontinteraction.command.Command;
import embasa.frontinteraction.model.Acsk;
import embasa.i18n.LanguageHolder;
import embasa.persistence.common.model.CommonLocalized;
import embasa.persistence.securedb.service.AcskService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetAvailableAcskTest {

    @Mock
    LanguageHolder languageHolder;

    @Mock
    AcskService acskService;

    @InjectMocks
    GetAvailableAcsk getAvailableAcsk = new GetAvailableAcsk();

    @Test
    public void getName() {
        assertEquals(getAvailableAcsk.getName(), "getAvailableAcsk");
    }

    @Test
    public void executeCmd() {
        when(languageHolder.getLanguageBy("ua")).thenReturn(LangUtil.LANG_UA);

        Request request = new Request(languageHolder);
        request.setRequest(getAvailableAcsk.getName());
        request.setEvent("acsk");
        try {
            when(acskService.findAll()).thenReturn(getAllAcsk());
            String result = getAvailableAcsk.executeCmd(request, null);

            Response<Acsk[]> response = Command.mapper.readValue(result,
                    new TypeReference<Response<Acsk[]>>() {});

            assertEquals(response.getStatus(), Response.OK);
            assertEquals(response.getEvent(), request.getEvent());
            Assert.assertArrayEquals(response.getData(), getAvailableAcsk.getAvailableAcsk(LangUtil.LANG_UA));
        } catch (Exception ex) {
            fail();
        }
    }

    private List<embasa.persistence.securedb.model.Acsk> getAllAcsk() {
        List<embasa.persistence.securedb.model.Acsk> result = new ArrayList<> ();
        embasa.persistence.securedb.model.Acsk acsk = new embasa.persistence.securedb.model.Acsk();
        acsk.setId(1L);
        acsk.setNameCode("ACSK of DFS");

        CommonLocalized loc = new CommonLocalized(LangUtil.LANG_UA);
        loc.setValue("АЦСК ІДД ДФС");
        acsk.addNameResource(loc);

        loc = new CommonLocalized(LangUtil.LANG_RU);
        loc.setValue("АЦСК ИДД ГФС");
        acsk.addNameResource(loc);

        acsk = new embasa.persistence.securedb.model.Acsk();
        acsk.setId(2L);
        acsk.setNameCode("ACSK of DFS");

        loc = new CommonLocalized(LangUtil.LANG_UA);
        loc.setValue("АЦСК ТОВ \"КС\"");
        acsk.addNameResource(loc);

        loc = new CommonLocalized(LangUtil.LANG_RU);
        loc.setValue("АЦСК ООО \"КС\"");
        acsk.addNameResource(loc);

        result.add(acsk);
        return result;
    }
}
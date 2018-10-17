package embasa.frontinteraction.command.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import embasa.LangUtil;
import embasa.frontinteraction.Request;
import embasa.frontinteraction.Response;
import embasa.frontinteraction.command.BadParametersException;
import embasa.frontinteraction.command.Command;
import embasa.frontinteraction.command.CommandHolderEmbasImpl;
import embasa.frontinteraction.command.CommandHolderGeneralImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetAvailableRequestsTest {

    private final String cmdHolderEmbasBeanName = "commandHolderEmbas";
    private final String cmdHolderGeneralBeanName = "commandHolderGeneral";

    @Mock
    private ApplicationContext appContext;

    @Mock
    CommandHolderEmbasImpl embasCmdHolder;

    @Mock
    CommandHolderGeneralImpl generalCmdHolder;

    private final Command[] EMBAS_COMMANDS = new Command[] { new GetAvailableAcsk(), new GetDBVersion(), new GetBackendVersion() };
    private final Command[] GENERAL_COMMANDS = new Command[] { new GetAvailableLanguages(), new GetCurrentUserInfo() };

    @Before
    public void init() {
        when(appContext.getBean(cmdHolderEmbasBeanName)).thenReturn(embasCmdHolder);
        when(appContext.getBean(cmdHolderGeneralBeanName)).thenReturn(generalCmdHolder);
        when(embasCmdHolder.getAvailableCommands()).thenReturn(EMBAS_COMMANDS);
        when(generalCmdHolder.getAvailableCommands()).thenReturn(GENERAL_COMMANDS);
    }

    @Test
    public void getName() {
        GetAvailableRequests cmd = new GetAvailableRequests(appContext, cmdHolderEmbasBeanName);
        assertEquals("getAvailableRequests", cmd.getName());
    }

    @Test
    public void executeCmd() throws BadParametersException, JsonProcessingException {
        GetAvailableRequests cmdEmbas = new GetAvailableRequests(appContext, cmdHolderEmbasBeanName);

        Request req = LangUtil.buildRequest();
        req.setLang(LangUtil.LANG_UA.getLangCode());
        req.setEvent("some_event");
        req.setRequest("getAvailableRequests");

        assertEquals(getAvailableCommands(req, EMBAS_COMMANDS), cmdEmbas.executeCmd(req, null));
        GetAvailableRequests cmdGeneral = new GetAvailableRequests(appContext, cmdHolderGeneralBeanName);
        assertEquals(getAvailableCommands(req, GENERAL_COMMANDS), cmdGeneral.executeCmd(req, null));
    }

    private String getAvailableCommands(Request request, Command[] cmds) throws JsonProcessingException {
        String[] commands = new String[cmds.length];
        for (int i = 0; i < cmds.length; i++) {
            commands[i] = cmds[i].getSignature();
        }
        Response<String[]> response = new Response<> ();
        response.setEvent(request.getEvent()).setStatus(Response.OK).setData(commands);
        return Command.mapper.writeValueAsString(response);
    }
}
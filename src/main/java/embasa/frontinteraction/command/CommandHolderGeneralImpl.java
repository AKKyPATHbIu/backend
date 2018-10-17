package embasa.frontinteraction.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/** Реалізація утримувача команд відкритого каналу. */
public class CommandHolderGeneralImpl implements CommandHolder {

    @Autowired
    @Qualifier(value = "getDBVersion")
    private Command getDBVersion;

    @Autowired
    @Qualifier(value = "getAvailableRequestsGeneral")
    private Command getAvailableRequests;

    @Autowired
    @Qualifier(value = "getBackendVersion")
    private Command getBackendVersion;

    @Autowired
    @Qualifier(value = "getAvailableLanguages")
    private Command getAvailableLanguages;

    @Autowired
    @Qualifier(value = "getAvailableAcsk")
    private Command getAvailableAcsk;

    @Override
    public Command[] getAvailableCommands() {
        return new Command[] { getAvailableRequests, getDBVersion, getBackendVersion, getAvailableLanguages, getAvailableAcsk };
    }
}

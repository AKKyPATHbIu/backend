package embasa.frontinteraction.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/** Реалізація утримувача команд закритого каналу. */
public class CommandHolderEmbasImpl implements CommandHolder {

    @Autowired
    @Qualifier(value = "getCurrentUserInfo")
    private Command getCurrentUserInfo;

    @Autowired
    @Qualifier(value = "changePassword")
    private Command changePassword;

    @Autowired
    @Qualifier(value = "getAvailableRequestsEmbas")
    private Command getAvailableRequests;

    @Override
    public Command[] getAvailableCommands() {
        return new Command[] { getAvailableRequests, getCurrentUserInfo, changePassword };
    }
}

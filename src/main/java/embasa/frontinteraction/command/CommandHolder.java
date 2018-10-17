package embasa.frontinteraction.command;

/** Утримувач команд. */
public interface CommandHolder {

    /**
     * Отримати доступні команди
     * @return доступні команди
     */
    Command[] getAvailableCommands();
}

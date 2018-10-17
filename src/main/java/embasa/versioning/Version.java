package embasa.versioning;

/** Версіонування додатків. */
public interface Version {

    /**
     * Перевіряє чи поточна версія старша за перевіряєму
     * @param version перевіряєма версія
     * @return true якщо поточна версія старша за перевіряєму
     */
    boolean after(Version version);

    /**
     * Перевіряє чи поточна версія молодша за перевіряєму
     * @param version перевіряєма версія
     * @return true якщо поточна версія молодша за перевіряєму
     */
    boolean before(Version version);

    /**
     * Порівняти версії
     * @param version перевіряєма версія
     * @return true якщо версії однакові
     */
    boolean equals(Version version);

    /**
     * Отримати старшу версію
     * @return старша версія
     */
    int getMajorVersion();

    /**
     * Отримати молодшу версію
     * @return молодша версія
     */
    int getMinorVersion();

    /**
     * Отримати версію патчу
     * @return версія патча
     */
    int getPatchVersion();

    /**
     * Отримати версію в строковому вигляді
     * @return версія в строковому вигляді
     */
    String toString();
}

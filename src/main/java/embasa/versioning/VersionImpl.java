package embasa.versioning;

/** Реалізація версіонування. */
public class VersionImpl implements Version {

    /** Старша версія. */
    private final Integer major;

    /** Молодша версія. */
    private final Integer minor;

    /** Версія патча. */
    private final Integer patch;

    /**
     * Конструктор
     * @param major старша версія
     * @param minor молодша версія
     * @param patch версія патча
     */
    public VersionImpl(int major, int minor, int patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    @Override
    public boolean after(Version version) {
        return major > version.getMajorVersion() ||
                major == version.getMajorVersion() && (minor > version.getMinorVersion() ||
                        minor == version.getMinorVersion() && patch > version.getPatchVersion());
    }

    @Override
    public boolean before(Version version) {
        return major < version.getMajorVersion() ||
                major == version.getMajorVersion() && (minor < version.getMinorVersion() ||
                        minor == version.getMinorVersion() && patch < version.getPatchVersion());
    }

    @Override
    public boolean equals(Version version) {
        return major == version.getMajorVersion() && minor == version.getMinorVersion() &&
                patch == version.getPatchVersion();
    }

    @Override
    public int getMajorVersion() {
        return major;
    }

    @Override
    public int getMinorVersion() {
        return minor;
    }

    @Override
    public int getPatchVersion() {
        return patch;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(major).append(".").append(minor).append(".").append(patch);
        return result.toString();
    }
}

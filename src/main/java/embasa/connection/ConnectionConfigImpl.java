package embasa.connection;

public class ConnectionConfigImpl implements ConnectionConfig {

    private String dialect = null;
    private String driver = null;
    private String url = null;
    private String username = null;
    private String password = null;

    /**
     * Встановити діалект
     * @param dialect нове значення діалекту
     * @return this
     */
    public ConnectionConfigImpl setDialect(String dialect) {
        this.dialect = dialect;
        return this;
    }

    /**
     * Встановити драйвер
     * @param driver нове значення драйвера
     * @return this
     */
    public ConnectionConfigImpl setDriver(String driver) {
        this.driver = driver;
        return this;
    }

    /**
     * Встановити url хоста бази даних
     * @param url нове значення url-у хоста бази даних
     * @return this
     */
    public ConnectionConfigImpl setUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * Встановити ім'я користувача
     * @param username нове значення імені користувача
     * @return this
     */
    public ConnectionConfigImpl setUsername(String username) {
        this.username = username;
        return this;
    }

    /**
     * Встановити пароль
     * @param password нове значення паролю
     * @return this
     */
    public ConnectionConfigImpl setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String getDialect() {
        return dialect;
    }

    @Override
    public String getDriver() {
        return driver;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }
}

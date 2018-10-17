package embasa.frontinteraction.command.params;

import java.io.Serializable;

/** Параметри команди авторизації. */
public class AuthorizeParam implements Serializable {

    /** Логін користувача. */
    private String login;

    /** Пароль користувача. */
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

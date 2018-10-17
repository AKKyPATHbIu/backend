package embasa.frontinteraction.command.params;

import java.io.Serializable;

/** Параметри команди зміни паролю. */
public class ChangePaswordParam implements Serializable {

    /** Старий пароль користувача. */
    private String oldPassword;

    /** Новий пароль користувача. */
    private String newPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}
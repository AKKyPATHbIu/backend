package embasa.persistence.securedb.model;

import embasa.persistence.common.model.BaseEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/** Користувач системи. */
public class User extends BaseEntity<Long> implements Serializable {

    /** Логін користувача. */
    private String username;

    /** ФІО користувача. */
    private String name;

    /** Електронна адреса користувача. */
    private String email;

    /** Ознака незаблокованності користувача. */
    private Boolean isEnabled;

    /** Коментар. */
    private String comment;

    /** Пароль користувача. */
    private String password;

    /** Дата до якої діє пароль. */
    private Date passwordExpiration;

    /** Конструктор за замовчанням. */
    public User() {
    }

    @Override
    public int hashCode() {
        return id == null ? Objects.hash(username) : id.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof User)) {
            return false;
        }

        User that = (User) o;
        return this.id == null ? this.username != null && this.username.equals(that.username) : this.id.equals(that.id);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String login) {
        this.username = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String passwd) {
        this.password = passwd;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public Date getPasswordExpiration() {
        return passwordExpiration;
    }

    public void setPasswordExpiration(Date passwordExpiration) {
        this.passwordExpiration = passwordExpiration;
    }
}

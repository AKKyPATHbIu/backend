package embasa.frontinteraction.model;

import java.io.Serializable;

/**
 * Клас користувача для відповіді на фронтенд<br>
 * Використовується при серіалізації/десеріалізації параметрів повідомлень на/від frontend-у у вигляді json
 */
public class User implements Serializable {

    /** Ідентифікатор користувача. */
    private Long id;

    /** ФІО користувача. */
    private String name;

    /** Електронна адреса користувача. */
    private String email;

    /** Коментарій. */
    private String comment;

    /** Ознака чи паротль дійсний за часом дії. */
    private Boolean isPasswordExpired = Boolean.FALSE;

    /** Конструктор за замовчанням. */
    public User() {
    }

    public Long getId() {
        return id;
    }

    public User setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public User setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public Boolean getIsPasswordExpired() {
        return isPasswordExpired;
    }

    public User setIsPasswordExpired(Boolean passwordExpired) {
        isPasswordExpired = passwordExpired;
        return this;
    }
}

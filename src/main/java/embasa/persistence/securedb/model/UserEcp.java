package embasa.persistence.securedb.model;

import embasa.persistence.common.model.BaseEntity;

import java.util.Date;
import java.util.Objects;

/** Електроний ключ користувача. */
public class UserEcp extends BaseEntity<Long> {

    /** Користувач, пов'язаний з ключем. */
    private User user;

    /** Ключ у двійковому вигляді. */
    private byte[] value;

    /** Час дії ключа. */
    private Date expire;

    /** АЦСК. */
    private Acsk acsk;

    /** Конструктор за замовчанням. */
    public UserEcp() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }

    public Acsk getAcsk() {
        return acsk;
    }

    public void setAcsk(Acsk acsk) {
        this.acsk = acsk;
    }

    @Override
    public int hashCode() {
        return id == null ? Objects.hash(user, value) : id.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof UserEcp)) {
            return false;
        }

        UserEcp that = (UserEcp) o;
        return this.value == null || that.value == null ? false : java.util.Arrays.equals(this.value, that.value);
    }
}

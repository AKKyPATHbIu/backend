package embasa.persistence.securedb.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/** Сутність версій бд. */
public class DBVersion implements Serializable {

    /** Версія бд. */
    private String version;

    /** Опис змін. */
    private String description;

    /** Дата дії версії. */
    private Date applyDate;

    /** Конструктор за замовчанням. */
    public DBVersion() {
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(version);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof DBVersion)) {
            return false;
        }

        DBVersion that = (DBVersion) o;
        return version == null ? false : this.version.equalsIgnoreCase(that.version);
    }
}

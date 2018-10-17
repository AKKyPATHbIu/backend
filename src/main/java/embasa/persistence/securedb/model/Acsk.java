package embasa.persistence.securedb.model;

import embasa.persistence.common.model.Language;
import embasa.persistence.common.model.BaseNameEntity;
import embasa.util.ObjUtil;

import java.io.Serializable;
import java.util.Objects;

/** Сутніть АЦСК. */
public class Acsk extends BaseNameEntity<Long> implements Serializable {

    /** Адреса cmp-сервера. */
    String cmpAddress;

    /** Порт cmp-сервера. */
    Integer cmpPort;

    /** Конструктор за замовчанням. */
    public Acsk() {
    }

    @Override
    public int hashCode() {
        return id == null ? Objects.hash(getNameCode()) : id.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Acsk)) {
            return false;
        }

        Acsk that = (Acsk) o;
        String nameCode = this.getNameCode();
        return this.id == null ? nameCode != null && nameCode.equals(that.getNameCode()) : this.id.equals(that.id);
    }

    public String getCmpAddress() {
        return cmpAddress;
    }

    public void setCmpAddress(String cmpAddress) {
        this.cmpAddress = cmpAddress;
    }

    public Integer getCmpPort() {
        return cmpPort;
    }

    public void setCmpPort(Integer cmpPort) {
        this.cmpPort = cmpPort;
    }

    public String getName(Language language) {
       return name.getResource(language);
    }
}

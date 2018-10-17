package embasa.persistence.maindb.model;

import embasa.persistence.common.model.BaseEntity;
import embasa.util.ObjUtil;

import java.io.Serializable;
import java.util.Objects;

/** Медичний заклад системи. */
public class Clinic extends BaseEntity<Long> implements Serializable {

    /** Опис медичного закладу. */
    private String description;

    /** Назва медичного закладу. */
    private String name;

    /** Статус медичного закладу. */
    private Long status;

    /** Конструктор за замовчанням. */
    public Clinic() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        return id == null ? Objects.hash(name, description, status) : id.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if ((this.name == null && this.description == null) || !(o instanceof Clinic)) {
            return false;
        }

        Clinic that = (Clinic) o;
        return this.id == null ? ObjUtil.equals(this.name, that.name) &&
                ObjUtil.equals(this.description, that.description) &&
                        ObjUtil.equals(this.status, that.status) : this.id.equals(that.id);
    }
}

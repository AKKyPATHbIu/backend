package embasa.persistence.securedb.model;

import embasa.persistence.common.model.BaseEntity;
import embasa.util.ObjUtil;

import java.io.Serializable;
import java.util.Objects;

/** Групи користувачів. */
public class Group extends BaseEntity<Long> implements Serializable {

    /** Ім'я групи. */
    private String name;

    /** Опис групи. */
    private String description;

    /** Конструктор за замовчанням. */
    public Group() {
    }

    @Override
    public int hashCode() {
        return id == null ? Objects.hash(name, description) : id.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Group)) {
            return false;
        }

        Group that = (Group) o;
        return this.id == null ? (this.name != null || this.description != null) && ObjUtil.equals(this.name, that.name) &&
                ObjUtil.equals(this.description, that.description) : this.id.equals(that.id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

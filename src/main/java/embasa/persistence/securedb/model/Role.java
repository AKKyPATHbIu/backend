package embasa.persistence.securedb.model;

import embasa.persistence.common.model.BaseEntity;
import embasa.util.ObjUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Ролі користувачів. */
public class Role extends BaseEntity<Long> implements Serializable {

    /** Імя ролі. */
    private String name;

    /** Опис ролі. */
    private String description;

    /** Списк дозволів ролі. */
    private final List<Permission> permissions = new ArrayList<Permission>();

    /** Конструктор за замовчанням. */
    public Role() {
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

        if (!(o instanceof Role)) {
            return false;
        }

        Role that = (Role) o;

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

    /**
     * Додати дозвіл
     * @param p дозвіл, що необхідно додати
     */
    public void addPermission(Permission p) {
        if (!permissions.contains(p)) {
            permissions.add(p);
        }
    }

    /**
     * Отримати список дозволів
     * @return список дозволів
     */
    public List<Permission> getPermissions() {
        return permissions;
    }
}

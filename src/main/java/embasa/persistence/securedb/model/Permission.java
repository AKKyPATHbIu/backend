package embasa.persistence.securedb.model;

import embasa.persistence.common.model.BaseNameDescEntity;
import embasa.util.ObjUtil;

import java.io.Serializable;
import java.util.Objects;

/** Дозвіл. */
public class Permission extends BaseNameDescEntity<Long> implements Serializable {

    /** Покажчик на модуль. */
    private Integer moduleId;

    /** Конструктор за замовчанням. */
    public Permission() {
    }

    @Override
    public int hashCode() {
        return id == null ? Objects.hash(getNameCode(), getDescCode()) : id.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Permission)) {
            return false;
        }

        Permission that = (Permission) o;
        String nameCode = this.getNameCode();
        String descrCode = this.getDescCode();
        return this.id == null ? (nameCode != null || descrCode != null) && ObjUtil.equals(nameCode, that.getNameCode()) &&
                ObjUtil.equals(descrCode, that.getDescCode()) &&
                        ObjUtil.equals(this.moduleId, that.moduleId) : this.id.equals(that.id);
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }
}

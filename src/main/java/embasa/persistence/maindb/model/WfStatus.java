package embasa.persistence.maindb.model;

import embasa.persistence.common.EntityList;
import embasa.persistence.common.model.BaseEntity;
import embasa.util.ObjUtil;

import java.util.Objects;

/** Статуси workflow. */
public class WfStatus extends BaseEntity<Long> {

    /** Покажчик на клініку. */
    private Long clinicId;

    /** Назва. */
    private String name;

    /** Опис. */
    private String descr;

    /** Сутность пов'язанної клініки. */
    private Clinic clinic;

    /** Список тригерів перехода статуса. */
    private final EntityList<WfTransitionTrigger> triggers = new EntityList();

    /** Список валідаторів перехода статуса. */
    private final EntityList<WfTransitionValidator> validators = new EntityList();

    /** Конструктор за замовчанням. */
    public WfStatus() {
    }

    public Long getClinicId() {
        return clinicId;
    }

    public WfStatus setClinicId(Long clinicId) {
        this.clinicId = clinicId;
        return this;
    }

    public String getName() {
        return name;
    }

    public WfStatus setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescr() {
        return descr;
    }

    public WfStatus setDescr(String descr) {
        this.descr = descr;
        return this;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public EntityList<WfTransitionTrigger> getTriggers() {
        return triggers;
    }

    public EntityList<WfTransitionValidator> getValidators() {
        return validators;
    }

    @Override
    public int hashCode() {
        return id == null ? Objects.hash(clinicId, name, descr) : id.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if ((this.id == null && this.clinicId == null) || !(o instanceof WfStatus)) {
            return false;
        }

        WfStatus that = (WfStatus) o;

        return ObjUtil.equals(this.id, that.id) && ObjUtil.equals(this.clinicId, that.clinicId) &&
                ObjUtil.equals(this.name, that.name) && ObjUtil.equals(this.descr, that.descr);
    }
}

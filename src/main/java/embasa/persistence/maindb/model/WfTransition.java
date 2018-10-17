package embasa.persistence.maindb.model;

import embasa.persistence.JdbcUtil;
import embasa.persistence.common.EntityList;
import embasa.persistence.common.model.BaseEntity;
import embasa.persistence.common.model.FieldsListable;
import embasa.util.ObjUtil;

import java.util.Objects;

/** Ланцюг переходів статусів workflow для сутності. */
public class WfTransition extends BaseEntity<Long> implements FieldsListable {

    /** Покажчик на пов'язану сутність конструктора. */
    private Long entityId;

    /** Покажчик на статус. */
    private Long statusId;

    /** Покажчик на наступний можливий для переходу статус. */
    private Long nextStatusId;

    /** Рівень ланки в ланцюжкі workflow. */
    private Integer level;

    /** Повязана сутність конструктора. */
    private CardEntity entity;

    /** Поточний статус. */
    private WfStatus status;

    /** Наступний для переходу статус. */
    private WfStatus nextStatus;

    /** Тригери перехода статуса. */
    private final EntityList<WfTransitionTrigger> triggers = new EntityList<> ();

    /** Валідатори перехода статуса. */
    private final EntityList<WfTransitionValidator> validators = new EntityList<> ();

    /** Конструктор за зуамовчанням. */
    public WfTransition() {
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Long getNextStatusId() {
        return nextStatusId;
    }

    public void setNextStatusId(Long nextStatusId) {
        this.nextStatusId = nextStatusId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public CardEntity getEntity() {
        return entity;
    }

    public void setEntity(CardEntity entity) {
        this.entity = entity;
    }

    public WfStatus getStatus() {
        return status;
    }

    public void setStatus(WfStatus status) {
        this.status = status;
    }

    public WfStatus getNextStatus() {
        return nextStatus;
    }

    public void setNextStatus(WfStatus nextStatus) {
        this.nextStatus = nextStatus;
    }

    public EntityList<WfTransitionTrigger> getTriggers() {
        return triggers;
    }

    public void addTrigger(WfTransitionTrigger trigger) {
        if (!triggers.contains(trigger)) {
            triggers.add(trigger);
        }
    }

    public EntityList<WfTransitionValidator> getValidators() {
        return validators;
    }

    public void addValidator(WfTransitionValidator validator) {
        if (!validators.contains(validator)) {
            validators.add(validator);
        }
    }

    @Override
    public int hashCode() {
        return id == null ? Objects.hash(entityId, statusId, nextStatusId, level) : id.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if ((this.id == null && this.statusId == null) || !(o instanceof WfTransition)) {
            return false;
        }

        WfTransition that = (WfTransition) o;
        return this.id == null ? ObjUtil.equals(this.entityId, that.entityId) &&
                ObjUtil.equals(this.statusId, that.statusId) && ObjUtil.equals(this.nextStatusId, that.nextStatusId) :
                        this.id.equals(that.id);
    }

    @Override
    public String listFieldsValues() {
        StringBuilder result = new StringBuilder();
        result.append(JdbcUtil.objToSqlStr(entityId));
        result.append(",").append(JdbcUtil.objToSqlStr(statusId));
        result.append(",").append(JdbcUtil.objToSqlStr(nextStatusId));
        result.append(",").append(JdbcUtil.objToSqlStr(level));
        result.append(",").append(validators.listFieldsValues()).append("::wf_transition_validators[]");
        result.append(",").append(triggers.listFieldsValues()).append("::wf_transition_triggers[]");
        return result.toString();
    }
}

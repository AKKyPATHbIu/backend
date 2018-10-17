package embasa.persistence.maindb.model;

import embasa.persistence.JdbcUtil;
import embasa.persistence.common.model.FieldsListable;
import embasa.util.ObjUtil;

import java.util.Objects;

/** Тригер перехода статусу workflow. */
public class WfTransitionTrigger implements FieldsListable {

    /** Покажчик на перехід. */
    private Long transitionId;

    /** Тригер перехода статусу workflow. */
    private Trigger trigger;

    /** Параметри тригера. */
    private String params;

    /** Конструктор за замовчанням. */
    public WfTransitionTrigger() {
    }

    public Long getTransitionId() {
        return transitionId;
    }

    public void setTransitionId(Long transitionId) {
        this.transitionId = transitionId;
    }

    public Trigger getTrigger() {
        return trigger;
    }

    public void setTrigger(Trigger trigger) {
        this.trigger = trigger;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    @Override
    public int hashCode() {
        return Objects.hash(transitionId, trigger, params);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof WfTransitionTrigger)) {
            return false;
        }

        WfTransitionTrigger that = (WfTransitionTrigger) o;
        return ObjUtil.equals(this.transitionId, that.transitionId) && ObjUtil.equals(this.trigger, that.trigger) &&
                ObjUtil.equals(this.params, that.params);
    }

    @Override
    public String listFieldsValues() {
        StringBuilder result = new StringBuilder();
        result.append("(");
        result.append(transitionId == null ? "0" : transitionId);
        result.append(",").append(trigger == null ? "" : JdbcUtil.objToStr(trigger.getId()));
        result.append(",");
        if (params != null) {
            result.append(JdbcUtil.objToStr(JdbcUtil.escapeQuoters(params)));
        }
        result.append(")");
        return result.toString();
    }
}

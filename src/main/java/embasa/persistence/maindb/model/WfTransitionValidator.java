package embasa.persistence.maindb.model;

import embasa.persistence.JdbcUtil;
import embasa.persistence.common.model.FieldsListable;
import embasa.util.ObjUtil;

import java.util.Objects;

/** Валідатор перехода статусу workflow. */
public class WfTransitionValidator implements FieldsListable {

    /** Покажчик на перехід. */
    private Long transitionId;

    /** Валідатор перехода. */
    private Validator validator;

    /** Параметри валідатора. */
    private String params;

    /** Конструктор за замовчанням. */
    public WfTransitionValidator() {
    }

    public Long getTransitionId() {
        return transitionId;
    }

    public void setTransitionId(Long transitionId) {
        this.transitionId = transitionId;
    }

    public Validator getValidator() {
        return validator;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    @Override
    public int hashCode() {
        return Objects.hash(transitionId, validator, params);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof WfTransitionValidator)) {
            return false;
        }

        WfTransitionValidator that = (WfTransitionValidator) o;
        return ObjUtil.equals(this.transitionId, that.transitionId) && ObjUtil.equals(this.validator, that.validator) &&
                ObjUtil.equals(this.params, that.params);
    }

    @Override
    public String listFieldsValues() {
        StringBuilder result = new StringBuilder();
        result.append("(").append(transitionId == null ? "0" : transitionId);
        result.append(",").append(validator == null ? "" : JdbcUtil.objToStr(validator.getId()));
        result.append(",");
        if (params != null) {
            result.append(JdbcUtil.objToStr(JdbcUtil.escapeQuoters(params)));
        }
        result.append(")");
        return result.toString();
    }
}

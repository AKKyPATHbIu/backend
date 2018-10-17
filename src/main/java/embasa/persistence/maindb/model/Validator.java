package embasa.persistence.maindb.model;

import embasa.persistence.JdbcUtil;
import embasa.persistence.common.LocalizedList;
import embasa.persistence.common.model.BaseNameDescEntity;
import embasa.persistence.common.model.FieldsListable;

import java.util.Objects;

/** Валідатор. */
public class Validator extends BaseNameDescEntity<Long> implements FieldsListable {

    /** Ідентифікатор типу даних. */
    private Long dataTypeId;

    /** Правило. */
    private String rule;

    /** Конструктор за замовчанням. */
    public Validator() {
    }

    public Long getDataTypeId() {
        return dataTypeId;
    }

    public Validator setDataTypeId(Long dataTypeId) {
        this.dataTypeId = dataTypeId;
        return this;
    }

    public LocalizedList getName() {
        return name;
    }

    public String getRule() {
        return rule;
    }

    public Validator setRule(String rule) {
        this.rule = rule;
        return this;
    }

    @Override
    public String listFieldsValues() {
        StringBuilder result = new StringBuilder();
        result.append("(");
        result.append(id == null ? "0" : id);
        result.append(",").append(JdbcUtil.objToStr(dataTypeId));
        result.append(",").append(JdbcUtil.objToStr(getName().getCode()));
        result.append(",").append(JdbcUtil.objToStr(getDescr().getCode()));
        result.append(",").append(JdbcUtil.objToStr(rule));
        result.append(")");
        return result.toString();
    }

    @Override
    public int hashCode() {
        return id == null ? Objects.hash(dataTypeId, getNameCode(), getDescCode()) : id.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if ((this.id == null && this.dataTypeId == null && this.getNameCode() == null && this.getDescCode() == null) ||
                !(o instanceof Validator)) {
            return false;
        }

        Validator obj = (Validator) o;
        return equals(this.id, obj.id) && equals(this.dataTypeId, obj.dataTypeId)
                && equals(this.getNameCode(), obj.getNameCode())
                        && equals(this.getDescCode(), obj.getDescCode());
    }

    private boolean equals(Object obj1, Object obj2) {
        return obj1 == null ? obj2 == null : obj1.equals(obj2);
    }
}

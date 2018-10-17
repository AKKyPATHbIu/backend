package embasa.persistence.maindb.model;

import embasa.persistence.JdbcUtil;
import embasa.persistence.common.model.BaseNameDescEntity;
import embasa.persistence.common.model.FieldsListable;
import embasa.util.ObjUtil;

import java.util.Objects;

/** Тригер. */
public class Trigger extends BaseNameDescEntity<Long> implements FieldsListable {

    /** Ідентифікатор модуля. */
    private Long moduleId;

    /** Конфігурація тригера.  */
    private String conf;

    /** Конструктор за замовчанням. */
    public Trigger() {
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public String getConf() {
        return conf;
    }

    public void setConf(String conf) {
        this.conf = conf;
    }

    @Override
    public String listFieldsValues() {
        StringBuilder result = new StringBuilder();
        result.append("(");
        result.append(id == null ? "0" : id);
        result.append(",").append(JdbcUtil.objToStr(moduleId));
        result.append(",").append(JdbcUtil.objToStr(name.getCode()));
        result.append(",").append(JdbcUtil.objToStr(descr.getCode()));
        result.append(",");
        if (conf != null) {
            result.append(JdbcUtil.objToStr(JdbcUtil.escapeQuoters(conf)));
        }
        result.append(")");
        return result.toString();
    }

    @Override
    public int hashCode() {
        return id == null ? Objects.hash(moduleId, name.getCode(), descr.getCode()) : id.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Trigger)) {
            return false;
        }

        Trigger that = (Trigger) o;
        return ObjUtil.equals(this.id, that.id) && ObjUtil.equals(this.moduleId, that.moduleId) &&
                ObjUtil.equals(this.name.getCode(), that.name.getCode()) &&
                ObjUtil.equals(this.descr.getCode(), that.descr.getCode()) &&
                ObjUtil.equals(this.conf, that.conf);
    }
}

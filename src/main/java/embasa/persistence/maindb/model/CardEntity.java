package embasa.persistence.maindb.model;

import embasa.persistence.JdbcUtil;
import embasa.persistence.common.EntityList;
import embasa.persistence.common.model.BaseEntity;
import embasa.persistence.common.model.FieldsListable;
import embasa.util.ObjUtil;

import java.util.List;
import java.util.Objects;

/** Сутність конструктора. */
public class CardEntity extends BaseEntity<Long> implements FieldsListable {

    /** Посилання на модуль. */
    private Long moduleId;

    /** Посиланя на медичний заклад. */
    private Long clinicId;

    /** Системна/користувацька сутість. */
    private Boolean isSystem = Boolean.FALSE;

    /** Назва сутності. */
    private String name;

    /** Опис сутності. */
    private String descr;

    /** Статус сутності. */
    private Long status;

    /** Назва таблиці, що відповідає сутності.  */
    private String tableName;

    /** Назва в'юшки, що відповідає сутності. */
    private String viewName;

    /** Параметри в форматі json для відображення сутності на UI. */
    private String uiConf;

    /** Перелік атрибутів сутності. */
    private final EntityList<CardEntityAttr> attr = new EntityList<> ();

    /** Конструктор за замовчанням. */
    public CardEntity() {
    }

    public Long getModuleId() {
        return moduleId;
    }

    public CardEntity setModuleId(Long moduleId) {
        this.moduleId = moduleId;
        return this;
    }

    public Long getClinicId() {
        return clinicId;
    }

    public CardEntity setClinicId(Long clinicId) {
        this.clinicId = clinicId;
        return this;
    }

    public boolean isSystem() {
        return isSystem;
    }

    public CardEntity setSystem(boolean system) {
        isSystem = system;
        return this;
    }

    public String getName() {
        return name;
    }

    public CardEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescr() {
        return descr;
    }

    public CardEntity setDescr(String descr) {
        this.descr = descr;
        return this;
    }

    public Long getStatus() {
        return status;
    }

    public CardEntity setStatus(Long status) {
        this.status = status;
        return this;
    }

    public String getTableName() {
        return tableName;
    }

    public CardEntity setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public String getViewName() {
        return viewName;
    }

    public CardEntity setViewName(String viewName) {
        this.viewName = viewName;
        return this;
    }

    public String getUiConf() {
        return uiConf;
    }

    public CardEntity setUiConf(String uiConf) {
        this.uiConf = uiConf;
        return this;
    }

    public EntityList<CardEntityAttr> getAttr() {
        return attr;
    }

    public void setAttr(List<CardEntityAttr> attr) {
        this.attr.clear();
        this.attr.addAll(attr);
    }

    @Override
    public String listFieldsValues() {
        StringBuilder result = new StringBuilder();
        result.append("(");
        result.append(id == null ? "0" : id);
        result.append(",").append(JdbcUtil.objToStr(moduleId));
        result.append(",").append(JdbcUtil.objToStr(clinicId));
        result.append(",").append(isSystem ? "TRUE" : "FALSE");
        result.append(",").append(JdbcUtil.objToStr(name));
        result.append(",").append(JdbcUtil.objToStr(descr));
        result.append(",").append(JdbcUtil.objToStr(status));
        result.append(",").append(JdbcUtil.objToStr(tableName));
        result.append(",").append(JdbcUtil.objToStr(viewName));
        result.append(",");
        if (uiConf != null) {
            result.append(JdbcUtil.escapeQuoters(uiConf));
        }
        result.append(")");
        return result.toString();
    }

    @Override
    public int hashCode() {
        return id == null ? Objects.hash(moduleId, clinicId, name, descr, tableName, viewName) : id.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if ((id == null && name == null) || !(o instanceof CardEntity)) {
            return false;
        }

        CardEntity that = (CardEntity) o;
        return ObjUtil.equals(this.id, that.id) && ObjUtil.equals(this.moduleId, that.moduleId) &&
                ObjUtil.equals(this.clinicId, that.clinicId) && ObjUtil.equals(this.name, that.name) &&
                ObjUtil.equals(this.tableName, that.tableName) && ObjUtil.equals(this.viewName, that.viewName);
    }
}

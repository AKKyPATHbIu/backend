package embasa.persistence.maindb.model;

import embasa.persistence.JdbcUtil;
import embasa.persistence.common.model.BaseEntity;
import embasa.persistence.common.model.FieldsListable;
import embasa.util.ObjUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Атрибут сутності конструктора {@link CardEntity}. */
public class CardEntityAttr extends BaseEntity<Long> implements FieldsListable {

    /** Показчик на пов'язану сутність конструктора. */
    private Long constrEntityId;

    /** Назва атрибуту. */
    private String name;

    /** Тип даних атрибуту. */
    private Long type;

    /** Опис атрибуту. */
    private String descr;

    private Boolean isAudited = Boolean.FALSE;

    /** Номер атрибуту за порядком для відображення. */
    private Long position;

    /** Посілання на іншу сутність, якщо  атрибут є екземпляром іншої сутності. */
    private Long refConstrEntityId;

    /** Набір параметрів в форматі json для відображення атрибуту на UI. */
    private String uiConf;

    /** Назва поля в табличці в БД , що відповідає атрибуту. */
    private String fieldName;

    /** Чи може атрибут бути порожнім. */
    private Boolean isNullable = Boolean.FALSE;

    /** Чи створювати індекс в БД в полі цього атрибуту. */
    private Boolean isIndexed = Boolean.FALSE;

    /** Статус атрибуту. */
    private Long status;

    /** Список валідаторів. */
    private final List<CardEntityValidator> validators = new ArrayList<>();

    /** Конструктор за замовчанням. */
    public CardEntityAttr() {
    }

    public Long getConstrEntityId() {
        return constrEntityId;
    }

    public CardEntityAttr setConstrEntityId(Long constrEntityId) {
        this.constrEntityId = constrEntityId;
        return this;
    }

    public String getName() {
        return name;
    }

    public CardEntityAttr setName(String name) {
        this.name = name;
        return this;
    }

    public Long getType() {
        return type;
    }

    public CardEntityAttr setType(Long type) {
        this.type = type;
        return this;
    }

    public String getDescr() {
        return descr;
    }

    public CardEntityAttr setDescr(String descr) {
        this.descr = descr;
        return this;
    }

    public Boolean getAudited() {
        return isAudited;
    }

    public CardEntityAttr setAudited(Boolean audited) {
        isAudited = audited;
        return this;
    }

    public Long getPosition() {
        return position;
    }

    public CardEntityAttr setPosition(Long position) {
        this.position = position;
        return this;
    }

    public Long getRefConstrEntityId() {
        return refConstrEntityId;
    }

    public CardEntityAttr setRefConstrEntityId(Long refConstrEntityId) {
        this.refConstrEntityId = refConstrEntityId;
        return this;
    }

    public String getUiConf() {
        return uiConf;
    }

    public CardEntityAttr setUiConf(String uiConf) {
        this.uiConf = uiConf;
        return this;
    }

    public String getFieldName() {
        return fieldName;
    }

    public CardEntityAttr setFieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public Boolean getNullable() {
        return isNullable;
    }

    public CardEntityAttr setNullable(Boolean nullable) {
        isNullable = nullable;
        return this;
    }

    public Boolean getIndexed() {
        return isIndexed;
    }

    public CardEntityAttr setIndexed(Boolean indexed) {
        isIndexed = indexed;
        return this;
    }

    public Long getStatus() {
        return status;
    }

    public CardEntityAttr setStatus(Long status) {
        this.status = status;
        return this;
    }

    public List<CardEntityValidator> getValidators() {
        return validators;
    }

    public void setValidators(List<CardEntityValidator> validators) {
        this.validators.clear();
        this.validators.addAll(validators);
    }

    @Override
    public String listFieldsValues() {
        StringBuilder result = new StringBuilder();
        result.append("(");
        result.append(id == null ? "0" : id);
        result.append(",").append(JdbcUtil.objToStr(constrEntityId));
        result.append(",").append(JdbcUtil.objToStr(name));
        result.append(",").append(JdbcUtil.objToStr(type));
        result.append(",").append(JdbcUtil.objToStr(descr));
        result.append(",").append(isAudited ? "TRUE" : "FALSE");
        result.append(",").append(JdbcUtil.objToStr(position));
        result.append(",").append(JdbcUtil.objToStr(refConstrEntityId));
        result.append(",");
        if (uiConf != null) {
            result.append(JdbcUtil.escapeQuoters(uiConf));
        }
        result.append(",").append(JdbcUtil.objToStr(fieldName));
        result.append(",").append(isNullable ? "TRUE" : "FALSE");
        result.append(",").append(isIndexed ? "TRUE" : "FALSE");
        result.append(",").append(JdbcUtil.objToStr(status));

        result.append(")");
        return result.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, constrEntityId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if ((this.constrEntityId == null && this.name == null) || !(o instanceof CardEntityAttr)) {
            return false;
        }

        CardEntityAttr obj = (CardEntityAttr) o;
        return this.id != null ? this.id.equals(obj.id) : ObjUtil.equals(this.constrEntityId, obj.constrEntityId) &&
                ObjUtil.equals(this.name, obj.name) &&
                ObjUtil.equals(this.type, obj.type) &&
                ObjUtil.equals(this.refConstrEntityId, obj.refConstrEntityId) &&
                ObjUtil.equals(this.fieldName, obj.fieldName);
    }
}
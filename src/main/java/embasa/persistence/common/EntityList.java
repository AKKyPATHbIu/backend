package embasa.persistence.common;

import embasa.persistence.common.model.FieldsListable;

import java.util.ArrayList;

/**
 * Список для збереження пов'язаних сутностей
 * @param <T> тип повязаних сутностей
 */
public class EntityList<T extends FieldsListable> extends ArrayList<T> implements FieldsListable {

    @Override
    public String listFieldsValues() {
        if (isEmpty()) {
            return "ARRAY[]";
        }

        StringBuilder result = new StringBuilder();
        result.append("ARRAY[");
        FieldsListable ent = get(0);
        result.append(ent.listFieldsValues());
        for (int i = 1; i < size(); i++) {
            result.append(",").append(get(i).listFieldsValues());
        }
        result.append("]");
        return result.toString().replace("(","'(").replace(")", ")'");
    }
}

package embasa.persistence.common.model;

/** Базовий клас для сутностей з ідентифікатором. */
public class BaseEntity<T> {

    /** Ідентифікатор. */
    protected T id;

    /**
     * Отримати ідентифікатор сутності
     * @return ідентифікатор сутності
     */
    public T getId() {
        return id;
    }

    /**
     * Встановити ідентифікатор сутності
     * @param id ідентифікатор сутності
     */
    public void setId(T id) {
        this.id = id;
    }
}

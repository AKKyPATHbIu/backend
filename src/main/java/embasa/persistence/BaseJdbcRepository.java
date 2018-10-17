package embasa.persistence;

import java.io.Serializable;
import java.util.List;

/** Базовий интерфейс репозиторія. */
public interface BaseJdbcRepository<T, PK extends Serializable> {

    /**
     * Знайти запис за ідентифікатором
     * @param id ідентифікатор запису
     * @return запис з ідентифікатором id
     */
    T findById(PK id);

    /**
     * Отримати перелік всіх записів таблиці
     * @return всі записи таблиці
     */
    List<T> findAll();

    /**
     * Зберегти сутність
     * @param p сутність, яку необхідно зберегти
     */
    void save(T p);

    /**
     * Відновити сутність
     * @param p сутність, яку необхідно відновити
     */
    void update(T p);

    /**
     * Видалити сутність
     * @param id ідентифікатор сутності, яку необхідно видалити
     */
    void delete(PK id);

    /**
     * Перевірити, чи існує сутність за ідентифікатором
     * @param id ідентифікатор сутності
     * @return true, якщо сутність з ідентифікатором id існує
     */
    boolean isExist(PK id);
}

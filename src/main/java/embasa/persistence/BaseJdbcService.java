package embasa.persistence;

import java.io.Serializable;
import java.util.List;

/**
 * Базовий інтерфейс сервісів
 * @param <T> тип entity
 * @param <PK> тип первинного ключа
 */
public interface BaseJdbcService<T, PK extends Serializable> {
    T findById(PK id);
    List<T> findAll();
    void save(T p);
    void update(T p);
    void delete(PK id);
    boolean isExist(PK id);
}

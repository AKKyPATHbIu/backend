package embasa.persistence;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/** Реалізація базового сервісу. */
public class BaseJdbcServiceImpl<T> implements BaseJdbcService<T, Long> {

    /** Репозиторій. */
    protected BaseJdbcRepository<T, Long> repository;

    /**
     * Встановити посилання на репозиторій
     * @param repository нове значення посилання на репозиторій
     */
    @Autowired
    public void setRepository(BaseJdbcRepository<T, Long> repository) {
        this.repository = repository;
    }

    @Override
    public T findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public void save(T p) {
        repository.save(p);
    }

    @Override
    public void update(T p) {
        repository.update(p);
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);
    }

    @Override
    public boolean isExist(Long id) {
        return repository.isExist(id);
    }
}

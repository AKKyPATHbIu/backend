package embasa.persistence.common.service;

import embasa.persistence.common.model.Language;

import java.util.List;

/** Сервіс мови. */
public interface LanguageService<T extends Language> {

    /**
     * Отримати всі об'єкти.
     * @return всі об'єкти
     */
    List<T> findAll();

    /**
     * Отримати об'єкт за ідентифікатором
     * @param code ідентифікатор (код) об'єкта
     * @return об'єкт за ідентифікатором
     */
    T findById(String code);
}

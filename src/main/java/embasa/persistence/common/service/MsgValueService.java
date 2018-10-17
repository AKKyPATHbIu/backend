package embasa.persistence.common.service;

import embasa.persistence.common.model.MsgValue;

import java.util.List;

/** Інтерфейс ресурсів локалізації. */
public interface MsgValueService {

    /**
     * Знайти за кодом
     * @param code код ресурсу
     * @return сутність за кодом ресурсу
     */
    List<MsgValue> findByCode(String code);

    /**
     * Знайти за кодом ресурсу і мови
     * @param code код ресурсу
     * @param langCode код мови
     * @return сутність за кодом ресурсу і мови
     */
    MsgValue findByCodeAndLang(String code, String langCode);

    /**
     * Зберегти сутність
     * @param p сутніть
     */
    void save(MsgValue p);

    /**
     * Відновити сутність
     * @param p сутніть
     */
    void update(MsgValue p);

    /**
     * Видалити сутність
     * @param code код ресурсу
     */
    void delete(String code);

    /**
     * Перевірити існування ресурсу за кодом
     * @param code код ресурсу
     * @return true якщо ресурс існує в базі
     */
    boolean isExists(String code);
}

package embasa.frontinteraction.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Ацск для frontend-а.<br>
 * Використовується при серіалізації/десеріалізації параметрів повідомлень на/від frontend-у у вигляді json
 */
public class Acsk implements Serializable {

    /** Код АЦСК. */
    private String code;

    /** Найменування АЦСК. */
    private String description;

    /** Конструктор за замовчанням. */
    public Acsk() {
    }

    /**
     * Отримати код АЦСК
     * @return код АЦСК
     */
    public String getCode() {
        return code;
    }

    /**
     * Встановити код АЦСК
     * @param code встановлюване значення
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Отримати опис АЦСК
     * @return опис АЦСК
     */
    public String getDescription() {
        return description;
    }

    /**
     * Встановити опис АЦСК
     * @param description встановлюване значення
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Acsk) || this.code == null) {
            return false;
        }

        return this.code.equals(((Acsk) o).code);
    }

    @Override
    public String toString() {
        return code + ", " + description;
    }
}

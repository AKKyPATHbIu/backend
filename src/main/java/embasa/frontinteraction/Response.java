package embasa.frontinteraction;

import java.io.Serializable;

/** Відповідь на frontend. */
public class Response<T extends Serializable> implements Serializable {

    /** Шаблон json-а відповіді без даних. */
    public transient static String TEMPLATE = "{ \"event\":\"%s\", \"status\":\"%s\",\"data\":\"\" }";

    /** Шаблон json-а відповіді з даними. */
    public transient static String TEMPLATE_WITH_DATA = "{ \"event\":\"%s\", \"status\":\"%s\",\"data\":\"%s\" }";

    /** Статус. */
    private Integer status = UNDEFINED;

    /** Тип події. */
    private String event;

    /** Відповідь. */
    private T data;

    /**
     * Встановити значення статусу
     * @param status нове значення статусу
     * @return this
     */
    public Response setStatus(int status) {
        this.status = status;
        return this;
    }

    /**
     * Отримати тип події
     * @return тип події
     */
    public String getEvent() {
        return event;
    }

    /**
     * Встановити тип події
     * @param event тип події
     * @return this
     */
    public Response setEvent(String event) {
        this.event = event;
        return this;
    }

    public T getData() {
        return data;
    }

    /**
     * Встановити дані
     * @param data дані
     * @return this
     */
    public Response setData(T data) {
        this.data = data;
        return this;
    }

    /**
     * Отримати значення статусу
     * @return значення статусу
     */
    public int getStatus() {
        return status;
    }

    // Коди відповідей

    /** Невизначено. */
    public static int UNDEFINED = -1;

    /** Успішна операція. */
    public static int OK = 200;

    /** Невдала операція. */
    public static int UNSUCCESS = 404;

    /** Невідомий метод.*/
    public static int UNKNOWN_METHOD = 401;

    /** Невірний формат запиту.*/
    public static int BAD_REQUEST = 402;
}

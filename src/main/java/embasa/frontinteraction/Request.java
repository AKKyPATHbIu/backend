package embasa.frontinteraction;

import com.fasterxml.jackson.annotation.JacksonInject;
import embasa.i18n.LanguageHolder;
import embasa.persistence.common.model.Language;

import java.io.Serializable;

/** Запит з фронтенда. */
public class Request implements Serializable {

    /** Утримувач мов системи. */
    private final LanguageHolder languageHolder;

    /** Версія клієнта. */
    private String clientVersion;

    /** Подія. */
    private String event;

    /** Код мови. */
    private String lang;

    /** Мова запиту. */
    private transient Language language = null;

    /** Метод. */
    private String request;

    /** Параметри методу. */
    private String transferData;

    /** Конструктор за замовчанням. */
    public Request(@JacksonInject final LanguageHolder languageHolder) {
        this.languageHolder = languageHolder;
    }

    /**
     * Отримати версію клієнта
     * @return версія клієнта
     */
    public String getClientVersion() {
        return clientVersion;
    }

    /**
     * Встановити версію клієнта
     * @param clientVersion нове значння версії клієнта
     * @return this
     */
    public Request setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
        return this;
    }

    /**
     * Отримати код події
     * @return код події
     */
    public String getEvent() {
        return event;
    }

    /**
     * Встановити код події
     * @param event нове значення коду події
     * @return this
     */
    public Request setEvent(String event) {
        this.event = event;
        return this;
    }

    /**
     * Отримати мову інтерфейсу користувача
     * @return мова інтерфейсу користувача
     */
    public String getLang() {
        return lang;
    }

    /**
     * Встановити мову інтерфейсу користувача
     * @param code нове значення мови інтерфейсу користувача
     * @return this
     */
    public Request setLang(String code) {
        this.lang = code;
        language = languageHolder.getLanguageBy(code);
        return this;
    }

    /**
     * Отримати код запросу
     * @return код запросу
     */
    public String getRequest() {
        return request;
    }

    /**
     * Встановити код запросу
     * @param request нове значення коду запросу
     * @return this
     */
    public Request setRequest(String request) {
        this.request = request;
        return this;
    }

    /**
     * Отримати параметри запиту
     * @return параметри запиту
     */
    public String getTransferData() {
        return transferData;
    }

    /**
     * Встановити параметри запиту
     * @param transferData нове значення параметрів запиту
     * @return this
     */
    public Request setTransferData(String transferData) {
        this.transferData = transferData;
        return this;
    }

    /**
     * Отримати мову запиту
     * @return мова запиту
     */
    public Language getLanguage() {
        return language;
    }
}

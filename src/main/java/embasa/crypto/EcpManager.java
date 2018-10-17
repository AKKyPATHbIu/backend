package embasa.crypto;

import embasa.persistence.securedb.model.Acsk;

/** Інтерфейс для роботи з криптобібліотекою. */
public interface EcpManager {

    /**
     * Прочитати приватний ключ
     * @param keyData приватний ключ в двійковому вигляді
     * @param password пароль приватного ключа
     * @return true в разі успіху
     */
    boolean readPrivateKeyBinary(byte[] keyData, String password);

    /**
     * Завантажити сертифікат з cmp-серверу
     * @param acsk акредитований центр сертифікації ключів
     * @return true в разі успіху
     */
    boolean loadCertificate(Acsk acsk);

    /**
     * Отримати інформацію про приватний ключ
     * @return інформація про приватний ключ
     */
    CertificateInfo getCertificateInfo();

    /** Фіналізувати роботу з бібліотекою. */
    void finalize();
}

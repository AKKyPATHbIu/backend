package embasa.crypto;

import com.iit.certificateAuthority.endUser.libraries.signJava.EndUser;

/**
 * Інтерфейс отримання об'єкту для роботи з криптою
 */
public interface CryptoManager {

    /**
     * Створити об'єкт {@link com.iit.certificateAuthority.endUser.libraries.signJava.EndUser} для роботи з криптобібліотекою
     * @return об'єкт для роботи з криптобібліотекою
     */
    EndUser getEndUser();
}

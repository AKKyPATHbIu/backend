package embasa.crypto;

import com.iit.certificateAuthority.endUser.libraries.signJava.*;
import org.apache.log4j.Logger;

import javax.annotation.PreDestroy;
import java.io.File;

/**
 * Реалізація інтерфейсу {@link CryptoManager}
 */
public class CryptoManagerImpl implements CryptoManager {

    private static Logger logger = Logger.getLogger(CryptoManagerImpl.class);

    /** Базовий каталог файлового сховища. */
    private static String STORE_PATH = (System.getProperty("catalina.base") == null ?
            System.getProperty("user.dir") : System.getProperty("catalina.base")) + File.separator + "certStore";

    /** Об'єкт для роботи з криптою.*/
    private static EndUser endUser;

    {
        File file = new File(STORE_PATH);
        if (!file.exists()) {
            file.mkdir();
        }

        EndUserResourceExtractor.SetPath(STORE_PATH);
        endUser = new EndUser();
        endUser.SetUIMode(false);
        endUser.SetLanguage(EndUser.EU_EN_LANG);
        try {
            endUser.Initialize();
            endUser.SetFileStoreSettings(buildFileStoreSettings(endUser));
            endUser.SetProxySettings(buildProxySettings(endUser));
            endUser.SetModeSettings(buildModeSettings(endUser));
        } catch (Exception ex) {
            logger.error(ex);
        }
    }

    /**
     * Створити об'єкт налаштування файлового сховища
     * @param endUser об'єкт для роботи з криптобібліотекою
     * @return об'єкт налаштування файлового сховища
     * @throws Exception
     */
    private static EndUserFileStoreSettings buildFileStoreSettings(EndUser endUser) throws Exception {
        EndUserFileStoreSettings settings = endUser.GetFileStoreSettings();
        settings.SetCheckCRLs(false);
        settings.SetPath(STORE_PATH);
        settings.SetOwnCRLsOnly(false);
        settings.SetAutoRefresh(false);
        settings.SetFullAndDeltaCRLs(false);
        settings.SetAutoDownloadCRLs(true);
        settings.SetSaveLoadedCerts(true);
        return settings;
    }

    /**
     * Створити об'єкт налаштування proxy
     * @param endUser об'єкт для роботи з криптобібліотекою
     * @return об'єкт налаштування proxy
     * @throws Exception
     */
    private static EndUserProxySettings buildProxySettings(EndUser endUser) throws Exception {
        EndUserProxySettings proxySettings = endUser.GetProxySettings();
        proxySettings.SetUseProxy(false);
        return proxySettings;
    }

    /**
     * Створити налаштування режиму роботи
     * @param endUser об'єкт для роботи з криптобібліотекою
     * @return налаштування режиму роботи
     * @throws Exception
     */
    private static EndUserModeSettings buildModeSettings(EndUser endUser) throws Exception {
        EndUserModeSettings modeSettings = endUser.GetModeSettings();
        modeSettings.SetOfflineMode(false);
        return modeSettings;
    }

    @Override
    public EndUser getEndUser() {
        return endUser;
    }

    @PreDestroy
    public void finalize() {
        if (endUser != null && endUser.IsInitialized()) {
            endUser.Finalize();
        }
    }
}

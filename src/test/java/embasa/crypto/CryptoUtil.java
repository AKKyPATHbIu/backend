package embasa.crypto;

import org.apache.tomcat.util.http.fileupload.FileUtils;

import java.io.File;
import java.io.IOException;

/** Допоміжна утиліта для роботи з криптою. */
public class CryptoUtil {

    /** Шлях до ключа. */
    public static String PATH = System.getProperty("user.dir") + File.separator + "certStore";

    /** Видалити сховище сертифікатів. */
    public static void deleteCertStore() {
        File file = new File(PATH);
        if (file.exists()) {
            try {
                FileUtils.deleteDirectory(file);
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }

}

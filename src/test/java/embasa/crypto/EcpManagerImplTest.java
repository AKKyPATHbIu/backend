package embasa.crypto;

import embasa.config.RootConfig;
import embasa.persistence.securedb.model.Acsk;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class,
        classes = { RootConfig.class })
@ActiveProfiles("DEV")
public class EcpManagerImplTest {

    @Autowired
    EcpManager ecpManager;

    /** Шлях до ключа. */
    private final String KEY_PATH = System.getProperty("user.dir") + File.separator + "src/test/resources/Key-6.dat";

    /** Пароль приватного ключа. */
    private final String PASSWORD = "stalker99";

    /** Код за ЄДРФО підприємця. */
    private final String DRFOCode = "2935907270";

    @Test
    public void test() {
        try {
            Path path = Paths.get(KEY_PATH);
            byte[] data = Files.readAllBytes(path);

            Acsk acsk = new Acsk();
            acsk.setCmpAddress("acskidd.gov.ua");
            acsk.setCmpPort(80);

            assertTrue(ecpManager.readPrivateKeyBinary(data, PASSWORD));
            assertTrue(ecpManager.loadCertificate(acsk));
            CertificateInfo certInfo = ecpManager.getCertificateInfo();
            assertNotNull(certInfo);
            assertEquals(DRFOCode, certInfo.getSubjDRFOCode());
        } catch (Exception ex) {
            System.out.println(ex);
            fail();
        }
    }

    @AfterClass
    public static void deleteCertStore() {
        CryptoUtil.deleteCertStore();
    }
}
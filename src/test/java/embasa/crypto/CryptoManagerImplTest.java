package embasa.crypto;

import com.iit.certificateAuthority.endUser.libraries.signJava.EndUser;
import org.junit.AfterClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.Assert.*;

@ActiveProfiles("DEV")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CryptoManagerImplTest {

    CryptoManagerImpl cryptoManager = new CryptoManagerImpl();

    @Test
    public void getEndUser() {
        EndUser endUser = cryptoManager.getEndUser();
        assertNotNull(endUser);
        assertTrue(endUser.IsInitialized());
    }

/*
    @Test
    public void testFinalize() {
        cryptoManager.finalize();
        assertFalse(cryptoManager.getEndUser().IsInitialized());
    }
*/

    @AfterClass
    public static void deleteCertStore() {
        CryptoUtil.deleteCertStore();
    }
}
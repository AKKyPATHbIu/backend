package embasa.crypto;

import com.iit.certificateAuthority.endUser.libraries.signJava.*;
import embasa.persistence.securedb.model.Acsk;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PreDestroy;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

/** Реалізація роботи з криптобібліотекою. */
public class EcpManagerImpl implements EcpManager {

    /** Логер. */
    private static Logger logger = Logger.getLogger(EcpManagerImpl.class);

    /** Об'єкт для роботи з криптобібліотекою. */
    private EndUser endUser;

    /** Контекст користувача. */
    private EndUserContext userContex = null;

    /** Контекст приватного ключа. */
    private EndUserPrivateKeyContext pkContext = null;

    /** Інформація про приватний ключ. */
    private EndUserPrivateKeyInfo pkInfo = null;

    /** Сертифікат в двійковому вигляді. */
    private byte[] certificate = null;

    /** Інформація про сертифікат. */
    private CertificateInfo certInfo = null;

    @Autowired
    public void setCryptoManager(CryptoManager cryptoManager) {
        endUser = cryptoManager.getEndUser();
    }

    @Override
    public boolean readPrivateKeyBinary(byte[] keyData, String password) {
        boolean result = false;
        try {
            userContex = endUser.CtxCreate();
            endUser.CtxSetParameter(userContex, EndUser.EU_CHECK_PRIVATE_KEY_CONTEXT_PARAMETER, false);
            endUser.CtxSetParameter(userContex, EndUser.EU_EXPORATABLE_CONTEXT_CONTEXT_PARAMETER, true);
            pkContext = endUser.CtxReadPrivateKeyBinary(userContex, keyData, password);
            pkInfo = endUser.GetKeyInfoBinary(keyData, password);
            userContex = endUser.CtxCreate();
            result = true;
        } catch (Exception ex) {
            logger.error(ex);
        }
        return result;
    }

    @Override
    public boolean loadCertificate(Acsk acsk) {
        if (certificate != null) {
            return true;
        }

        ArrayList<String> cmpAddress = new ArrayList<> ();
        cmpAddress.add(acsk.getCmpAddress());
        ArrayList<String> cmpPort = new ArrayList<> ();
        cmpPort.add(acsk.getCmpPort().toString());

        try {
            byte[] certs = endUser.GetCertificatesByKeyInfo(pkInfo, cmpAddress, cmpPort);
            certificate = extractCertificateForm(certs);
        } catch (Exception ex) {
            logger.error(ex);
            ex.printStackTrace();
        }

        return certificate != null;
    }

    /**
     * Витягти сертифікат із відповіді сmp-серверу
     * @param cmpResponse відповідь сmp-серверу
     * @return сертифікат
     */
    private static byte[] extractCertificateForm(byte[] cmpResponse) {
        int offset = 45;
        if (cmpResponse.length > offset + 4 && cmpResponse[offset] == 0x30) {
            byte[] certLength = new byte[] { cmpResponse[offset + 2], cmpResponse[offset + 3] };
            int bytesCount = new BigInteger(certLength).intValue();
            return Arrays.copyOfRange(cmpResponse, offset, offset + bytesCount + 4);
        } else {
            return null;
        }
    }

    @Override
    public CertificateInfo getCertificateInfo() {
        if (certInfo == null) {
            try {
                EndUserCertificateInfoEx infoEx = endUser.ParseCertificateEx(certificate);

                certInfo = new CertificateInfo();
                certInfo.setIssuer(infoEx.GetIssuer());
                certInfo.setIssuerCN(infoEx.GetIssuerCN());
                certInfo.setSerial(infoEx.GetSerial());
                certInfo.setSubject(infoEx.GetSubject());
                certInfo.setSubjAddress(infoEx.GetSubjAddress());
                certInfo.setSubjCN(infoEx.GetSubjCN());
                certInfo.setSubjOrg(infoEx.GetSubjOrg());
                certInfo.setSubjOrgUnit(infoEx.GetSubjOrgUnit());
                certInfo.setSubjTitle(infoEx.GetSubjTitle());
                certInfo.setSubjState(infoEx.GetSubjState());
                certInfo.setSubjLocality(infoEx.GetSubjLocality());
                certInfo.setSubjFullName(infoEx.GetSubjFullName());
                certInfo.setSubjAddress(infoEx.GetSubjAddress());
                certInfo.setSubjPhone(infoEx.GetSubjPhone());
                certInfo.setSubjEmail(infoEx.GetSubjEMail());
                certInfo.setSubjDNS(infoEx.GetSubjDNS());
                certInfo.setSubjEDRPOUCode(infoEx.GetSubjEDRPOUCode());
                certInfo.setSubjDRFOCode(infoEx.GetSubjDRFOCode());
                certInfo.setPkBeginTime(infoEx.GetPrivKeyBeginTime()).setPkEndTime(infoEx.GetPrivKeyEndTime());
                certInfo.setCertBeginTime(infoEx.GetCertBeginTime()).setCertEndTime(infoEx.GetCertEndTime());
            } catch (Exception ex) {
                logger.error(ex);
            }
        }
        return certInfo;
    }

    @PreDestroy
    public void finalize() {
        if (endUser != null && endUser.IsInitialized()) {
            if (pkContext != null) {
                try {
                    endUser.CtxFreePrivateKey(pkContext);
                } catch (Exception ex) {
                    logger.error(ex);
                }
                pkContext = null;
            }

            if (userContex != null) {
                try {
                    endUser.CtxFree(userContex);
                    userContex = null;
                } catch (Exception ex) {
                    logger.error(ex);
                }
            }
        }
    }
}

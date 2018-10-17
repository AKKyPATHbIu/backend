package embasa.filters;

import embasa.crypto.CertificateInfo;
import embasa.crypto.EcpManager;
import embasa.i18n.LocaleUtil;
import embasa.i18n.Localizer;
import embasa.persistence.securedb.model.Acsk;
import embasa.persistence.securedb.model.User;
import embasa.persistence.securedb.model.UserEcp;
import embasa.persistence.securedb.service.UserEcpService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EcpAuthFilterTest {

    @Mock
    Localizer localizer;

    @Mock
    UserEcpService userEcpService;

    @Mock
    EcpManager ecpManager;

    @InjectMocks
    EcpAuthFilter ecpFilter = new EcpAuthFilter() {
        @Override
        public EcpManager getEcpManager(HttpServletRequest request) {
            return ecpManager;
        }
    };

    private final String KEY_BASE_64 = "MIIDrjAcBgwrBgEEAYGXRgEBAQIwDAQEnTho/AQEugu9FwSCA4x1PEkQ/dF2A+LcU4S8VgXfEuo2/XPCjGcabouNLFWnjy/JwvlIGj6QkihZidZ03SCwL6VqgrOcAh/STX9/g5OCi2FNflZaQ7QtYr3lJMyV/7maRnqgvQRvY5JXew0mOnWldr5oPOh0Tu+xc4FZ0Ipk/bNmi2OQYpMT17twnSFaqX30sTQLEu91aFDJNxx+Td4j0BY3oLu2KXDKSGWoVltUOBeCA9DtWx56aLi61/DBZgBR1O9CMFmTGPX0vS6swmlhHRs4OtlrbfslHtz5ktpMgtdG1Nf3ErzGUQBvVWHfFd4x/qrE36Eik2PGjMASzT+FeON5GVAckeYrHRdsV5dm17pEf3qJh06Q4LRRGrVJD6sRZvzEeTgyqvW3CrIoS9CbTlIZ9nnfxIQvAK+EaRTk4KQDVmF8CwikElVdl9nsYqCBbSyxCJQNeWkXFc1md8w4OJgurUAqkv9HXSRIjypJ4faLTcqDXLrXukR/eomHTibXhsK3fPc8ck9JeONqAd7y1k6fCk1AgO9fr3WUJ5EPc5oMPiUN2Jjo2Mc7azEaQL/OWahL+6gqmAOTt8AkoRRbpb+Yaq4kZij6HJ3EX7sQV7GViqaooLHGySnVu4zbU+ac0DowC3XAgNNmW70R9KkhSiui+2X3G/vtFlZhTW0KUU45i5ON3BPJ2qXe0NCUoYskBiYqZqfxhEwQuoRMaIFiPMxNA4uqJwGlKBg7S+2+AaUoGDtL7b4BpSgYO0vtvsgEMS6aBV/jmu0ekieBtpg1NJGhfSsBwrquXivauRsqQwAknViU41Aror+pSV4QF6jv5FKGZeQa6K40J/TLmKB+/rwklUGniyrssL0GfRsW4AC6rm3nsRhsr00RQ/uRSAbHApm/LEKdZFCIX1t6x4/LF304WQAXo9TQziFHwXLxCuhWd+AvaIsmKL7uiK1+yf/ErXiSIb2ODy7OLJ2k7WVogaBX27eU3SQW4Ckga9nbruV9D9yIgZBY/EENlpzqIZN9qUsZXGsS5wMO/ubeWoP/heo93STefmqydxl++ihIwPi3auQoQKikEuMizWVJqtYpH8C4vL4xPeWrALXrELittp+XApuvFjR6JJ4WzzpDP9cbTc5qM5B3ixoxgfuo5dLM2BTDqm5BAnS5Oj4ZAOwyiUEpT/ShrZgpA5Ce+u8fF5Oh186ky5TgFnB0Iq5IBK8BlbGqgsQvrC6LEw==";

    private final String KEY_PASSWORD = "stalker99";

    private final String BAD_ACSK = "ZZZ";

    @Test
    public void doFilterInternal() {
        Locale locale = LocaleUtil.LOCALE_UA;

        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        when(httpServletRequest.getHeader(EcpAuthFilter.KEY_HEADER_NAME)).thenReturn(KEY_BASE_64);
        when(httpServletRequest.getHeader(EcpAuthFilter.LANG_HEADER_NAME)).thenReturn("ua");

        byte[] keyData = Base64.getDecoder().decode(KEY_BASE_64.getBytes());
        UserEcp userEcp = new UserEcp();

        when(localizer.getMessage("userIsBlocked", locale)).thenReturn("Користувача %s заблоковано в системі!");
        when(localizer.getMessage("badPassword", locale)).thenReturn("Невірний пароль!");
        when(localizer.getMessage("acskNotSet", locale)).thenReturn("Не задано АЦСК для приватного ключа!");
        when(localizer.getMessage("errorLoadingCertificate", locale)).thenReturn("Трапилася помилка при завантаженні сертифіката!");
        when(localizer.getMessage("privateKeyExpired", locale)).thenReturn("Час дії приватного ключа минув або ще не наступив!");
        when(localizer.getMessage("userNotFoundByPrivateKey", locale)).thenReturn("Не знайдено жодного користувача, пов'язаного з ключем!");

        when(userEcpService.findByEdsKey(keyData)).thenReturn(null);

        try {
            ecpFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);
            fail();
        } catch (Exception ex) {
            assertEquals("Не знайдено жодного користувача, пов'язаного з ключем!", ex.getMessage());
        }

        User user = new User();
        user.setEnabled(false);
        user.setName("test");
        userEcp.setUser(user);

        when(userEcpService.findByEdsKey(keyData)).thenReturn(userEcp);

        try {
            ecpFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);
            fail();
        } catch (Exception ex) {
            assertEquals(String.format("Користувача %s заблоковано в системі!", user.getName()), ex.getMessage());
        }

        user.setEnabled(true);

        when(ecpManager.readPrivateKeyBinary(any(byte[].class), any(String.class))).thenReturn(false);

        try {
            ecpFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);
            fail();
        } catch (Exception ex) {
            assertEquals("Невірний пароль!", ex.getMessage());
        }

        when(ecpManager.readPrivateKeyBinary(any(byte[].class), any(String.class))).thenReturn(true);

        try {
            ecpFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);
            fail();
        } catch (Exception ex) {
            assertEquals("Не задано АЦСК для приватного ключа!", ex.getMessage());
        }

        Acsk acsk = new Acsk();
        acsk.setCmpAddress("ca.ksystems.com.ua");
        acsk.setCmpPort(80);
        userEcp.setAcsk(acsk);

        try {
            ecpFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);
            fail();
        } catch (Exception ex) {
            assertEquals("Трапилася помилка при завантаженні сертифіката!", ex.getMessage());
        }

        CertificateInfo certInfo = mock(CertificateInfo.class);
        when(certInfo.isExpired()).thenReturn(true);
        when(ecpManager.loadCertificate(acsk)).thenReturn(true);
        when(ecpManager.getCertificateInfo()).thenReturn(certInfo);

        try {
            ecpFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);
            fail();
        } catch (Exception ex) {
            assertEquals("Час дії приватного ключа минув або ще не наступив!", ex.getMessage());
        }

        when(certInfo.isExpired()).thenReturn(false);

        try {
            ecpFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);
        } catch (Exception ex) {
            fail();
        }

        verify(ecpManager, times(5)).finalize();
    }
}
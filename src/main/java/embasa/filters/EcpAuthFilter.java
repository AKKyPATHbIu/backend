package embasa.filters;

import embasa.crypto.CertificateInfo;
import embasa.crypto.EcpManager;
import embasa.i18n.LocaleUtil;
import embasa.i18n.Localizer;
import embasa.persistence.securedb.model.Acsk;
import embasa.persistence.securedb.model.User;
import embasa.persistence.securedb.model.UserEcp;
import embasa.persistence.securedb.service.UserEcpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Locale;

/** Авторизація ЄЦП. */
public class EcpAuthFilter extends OncePerRequestFilter {

    /** Заголовок з ЄЦП. */
    public static String KEY_HEADER_NAME = "X-Auth-Key";

    /** Мова інтерфейсу фронтенду. */
    public static String LANG_HEADER_NAME = "X-Auth-Lang";

    /** Користувача %s заблоковано в системі! */
    private final String USER_IS_BLOCKED_MSG = "userIsBlocked";

    /** Не задано АЦСК для приватного ключа! */
    private final String ACSK_NOT_SET_MSG = "acskNotSet";

    /** Невірний пароль! */
    private final String BAD_PASSWORD_MSG = "badPassword";

    /** Трапилася помилка при завантаженні сертифіката! */
    private final String ERROR_LOADING_CERTIFICATE_MSG = "errorLoadingCertificate";

    /** Час дії приватного ключа минув або ще не наступив! */
    private final String PRIVATE_KEY_EXPIRED_MSG = "privateKeyExpired";

    /** Не знайдено жодного користувача, пов'язаного з ключем! */
    private final String USER_NOT_FOUND_BY_PRIVATE_KEY_MSG = "userNotFoundByPrivateKey";

    /** Сервіс {@link UserEcp}. */
    private UserEcpService userEcpService;

    /** Об'єкт локалізації. */
    private Localizer localizer;

    @Autowired
    public void setLocalizer(Localizer localizer) {
        this.localizer = localizer;
    }

    @Autowired
    @Qualifier("secureDBUserEcpService")
    public void setUserEcpService(UserEcpService userEcpService) {
        this.userEcpService = userEcpService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            FilterChain filterChain) throws ServletException, IOException {

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            String key = httpServletRequest.getHeader(KEY_HEADER_NAME);
            String langCode = httpServletRequest.getHeader(LANG_HEADER_NAME);
            Locale locale = LocaleUtil.parseLocaleBy(langCode);

            if (StringUtils.hasText(key)) {
                byte[] keyData = Base64.getDecoder().decode(key.getBytes());
                UserEcp userEcp = userEcpService.findByEdsKey(keyData);
                User user = userEcp == null ? null : userEcp.getUser();
                if (user != null) {
                    if (!user.isEnabled()) {
                        throw new BadCredentialsException(String.format(
                                localizer.getMessage(USER_IS_BLOCKED_MSG, locale), user.getName()));
                    }

                    String password = httpServletRequest.getHeader("password");

                    EcpManager ecpManager = getEcpManager(httpServletRequest);

                    try {
                        if (!ecpManager.readPrivateKeyBinary(keyData, password)) {
                            throw new BadCredentialsException(localizer.getMessage(BAD_PASSWORD_MSG, locale));
                        }

                        Acsk acsk = userEcp.getAcsk();

                        if (acsk == null) {
                            throw new BadCredentialsException(String.format(localizer.getMessage(ACSK_NOT_SET_MSG, locale)));
                        }

                        if (!ecpManager.loadCertificate(acsk)) {
                            throw new BadCredentialsException(
                                    localizer.getMessage(ERROR_LOADING_CERTIFICATE_MSG, locale));
                        }

                        CertificateInfo certInfo = ecpManager.getCertificateInfo();
                        if (certInfo.isExpired()) {
                            throw new BadCredentialsException(localizer.getMessage(PRIVATE_KEY_EXPIRED_MSG, locale));
                        }
                    } catch (BadCredentialsException ex) {
                        ecpManager.finalize();
                        throw ex;
                    }
                    ecpManager.finalize();

                    List<GrantedAuthority> authorities = new ArrayList<>();
                    authorities.add((GrantedAuthority) () -> "ROLE_USER");
                    Authentication auth = new UsernamePasswordAuthenticationToken(user.getId(), null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                } else {
                    throw new BadCredentialsException(
                            localizer.getMessage(USER_NOT_FOUND_BY_PRIVATE_KEY_MSG, locale));
                }
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    /**
     * Отримати бін для роботи з криптою
     * @param request запит
     * @return бін для роботи з криптою
     */
    public EcpManager getEcpManager(HttpServletRequest request) {
        ServletContext sc = request.getServletContext();
        ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(sc);
        return appContext.getBean(EcpManager.class);
    }
}

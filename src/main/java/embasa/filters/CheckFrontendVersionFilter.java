package embasa.filters;

import embasa.i18n.LocaleUtil;
import embasa.i18n.Localizer;
import embasa.util.PropertiesHolder;
import embasa.versioning.FrontendVersionChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/** Перевірка сумісності версій frontend-у і backend-у. */
public class CheckFrontendVersionFilter extends OncePerRequestFilter {

    /** Версія frontend-у. */
    public static String FRONTEND_VERSION_HEADER_NAME = "X-Auth-Client-Version";

    /** Версія frontend-у в заголовку https-запиту авторизації є обов'язковою!. */
    public static String FRONTEND_VERSION_REQUIRED_MSG = "frontendVersionRequired";

    /** Версія frontend-у: %s, не сумісна з мінімально допустимою версією: %s!. */
    public static String WRONG_FRONTEND_VERSION_MSG = "wrongFrontendVersion";

    /** Мінімально допустима версія frontend-у. */
    private String minFrontendVersion;

    /** Об'єкт для перевірки сумісності версій frontend-у і backend-у. */
    private FrontendVersionChecker versionChecker;

    /** Об'єкт локалізації. */
    private Localizer localizer;

    @Autowired
    public void setLocalizer(Localizer localizer) {
        this.localizer = localizer;
    }

    @Autowired
    public void setVersionChecker(FrontendVersionChecker versionChecker) {
        this.versionChecker = versionChecker;
    }

    @Autowired
    public void setPropertiesHolder(PropertiesHolder propertiesHolder) {
        minFrontendVersion = propertiesHolder.get(PropertiesHolder.MIN_FRONTEND_VERSION);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            FilterChain filterChain) throws IOException, ServletException, BadCredentialsException {

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            String langCode = httpServletRequest.getHeader(EcpAuthFilter.LANG_HEADER_NAME);
            Locale locale = LocaleUtil.parseLocaleBy(langCode);

            String frontendVersion = httpServletRequest.getHeader(FRONTEND_VERSION_HEADER_NAME);
            if (!StringUtils.hasText(frontendVersion)) {
                throw new BadCredentialsException(localizer.getMessage(FRONTEND_VERSION_REQUIRED_MSG, locale));
            }

            try {
                if (!versionChecker.isValid(frontendVersion, locale)) {
                    String errorMsg = localizer.getMessage(WRONG_FRONTEND_VERSION_MSG, locale);
                    throw new BadCredentialsException(String.format(errorMsg, frontendVersion, minFrontendVersion));
                }
            } catch (IllegalArgumentException ex) {
                throw new BadCredentialsException(ex.getMessage());
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}

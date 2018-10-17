package embasa.filters;

import embasa.i18n.LocaleUtil;
import embasa.i18n.Localizer;
import embasa.util.PropertiesHolder;
import embasa.versioning.FrontendVersionChecker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CheckFrontendVersionFilterTest {

    /** Сумісна версія фронтенду в строковому вигляді. */
    private final String GOOD_VERSION = "1.2.3";

    /** Мінімально допустима версія. */
    private final String MIN_VERSION = "1.2.0";

    /** Не сумісна версія фроненду в строковому вигляді. */
    private final String BAD_VERSION = "0.0.3";

    /** Повідомлення обов'язковості версії frontend-у в заголовку https-запиту. */
    private final String FRONTEND_VERSION_REQUIRED_MSG =  "Версія frontend-у в заголовку https-запиту авторизації є обов'язковою!";

    /** Повідомлення про несумісність версій frontend-у і backend-у. */
    private final String WRONG_FRONTEND_VERSION_MSG = "Версія frontend-у: %s, не сумісна з мінімально допустимою версією: %s!";

    @Mock
    /** Об'єкт для перевірки сумісності версій frontend-у і backend-у. */
    private FrontendVersionChecker versionChecker;

    @Mock
    /** Об'єкт локалізації. */
    private Localizer localizer;

    @Mock
    /** Утримувач налаштувань додатку. */
    private PropertiesHolder propertiesHolder;

    @InjectMocks
    private final CheckFrontendVersionFilter filter = new CheckFrontendVersionFilter();

    @Test
    public void doFilterInternal() {
        Locale locale = LocaleUtil.LOCALE_UA;

        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        when(httpServletRequest.getHeader(EcpAuthFilter.LANG_HEADER_NAME)).thenReturn("ua");
        when(httpServletRequest.getHeader(CheckFrontendVersionFilter.FRONTEND_VERSION_HEADER_NAME)).thenReturn(GOOD_VERSION);
        when(versionChecker.isValid(GOOD_VERSION, locale)).thenReturn(true);

        try {
            filter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);
        } catch (Exception ex) {
            fail();
        }

        when(httpServletRequest.getHeader(CheckFrontendVersionFilter.FRONTEND_VERSION_HEADER_NAME)).thenReturn(null);
        when(localizer.getMessage("frontendVersionRequired", locale)).thenReturn(FRONTEND_VERSION_REQUIRED_MSG);
        try {
            filter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);
        } catch (Exception ex) {
            assertEquals(FRONTEND_VERSION_REQUIRED_MSG, ex.getMessage());
        }

        when(httpServletRequest.getHeader(CheckFrontendVersionFilter.FRONTEND_VERSION_HEADER_NAME)).thenReturn("");
        try {
            filter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);
        } catch (Exception ex) {
            assertEquals(FRONTEND_VERSION_REQUIRED_MSG, ex.getMessage());
        }

        when(httpServletRequest.getHeader(CheckFrontendVersionFilter.FRONTEND_VERSION_HEADER_NAME)).thenReturn("   ");
        try {
            filter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);
        } catch (Exception ex) {
            assertEquals(FRONTEND_VERSION_REQUIRED_MSG, ex.getMessage());
        }

        when(httpServletRequest.getHeader(CheckFrontendVersionFilter.FRONTEND_VERSION_HEADER_NAME)).thenReturn(BAD_VERSION);
        when(versionChecker.isValid(BAD_VERSION, locale)).thenReturn(false);
        when(localizer.getMessage("wrongFrontendVersion", locale)).thenReturn(WRONG_FRONTEND_VERSION_MSG);
        when(propertiesHolder.get(PropertiesHolder.MIN_FRONTEND_VERSION)).thenReturn(MIN_VERSION);
        filter.setPropertiesHolder(propertiesHolder);
        try {
            filter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);
        } catch (Exception ex) {
            assertEquals(String.format(WRONG_FRONTEND_VERSION_MSG, BAD_VERSION, MIN_VERSION), ex.getMessage());
        }
    }

}
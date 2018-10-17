package embasa.versioning;

import embasa.i18n.LocaleUtil;
import embasa.util.PropertiesHolder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FrontendVersionCheckerImplTest {

    /** Перевіряєма версія в строковому вигляді. */
    private String CHECKED_VERSION_STR = "1.2.3";

    /** Перевіряєма версія. */
    private final Version CHECKED_VERSION = new VersionImpl(1, 2, 3);

    /** Нижча за перевіряєму версія в строковому вигляді. */
    private String LOWER_VERSION_STR = "0.9.5";

    /** Нижча за перевіряєму версія. */
    private final Version LOWER_VERSION = new VersionImpl(0, 9, 5);

    /** Більша за перевіряєму версія в строковому вигляді. */
    private String UPPER_VERSION_STR = "2.3.0";

    /** Більша за перевіряєму версія. */
    private final Version UPPER_VERSION = new VersionImpl(2, 3, 0);

    @Mock
    PropertiesHolder propertiesHolder;

    @Mock
    VersionParser versionParser;

    @Test
    public void isValid() {
        when(propertiesHolder.get(PropertiesHolder.MIN_FRONTEND_VERSION)).thenReturn(LOWER_VERSION_STR);
        when(versionParser.parse(CHECKED_VERSION_STR, LocaleUtil.LOCALE_UA)).thenReturn(CHECKED_VERSION);
        when(versionParser.parse(LOWER_VERSION_STR, LocaleUtil.LOCALE_UA)).thenReturn(LOWER_VERSION);

        FrontendVersionChecker versionChecker = new FrontendVersionCheckerImpl(versionParser, propertiesHolder);

        assertTrue(versionChecker.isValid(CHECKED_VERSION_STR, LocaleUtil.LOCALE_UA));

        when(propertiesHolder.get(PropertiesHolder.MIN_FRONTEND_VERSION)).thenReturn(UPPER_VERSION_STR);
        when(versionParser.parse(UPPER_VERSION_STR, LocaleUtil.LOCALE_UA)).thenReturn(UPPER_VERSION);
        versionChecker = new FrontendVersionCheckerImpl(versionParser, propertiesHolder);
        assertFalse(versionChecker.isValid(CHECKED_VERSION_STR, LocaleUtil.LOCALE_UA));
    }
}
package embasa.versioning;

import embasa.i18n.LocaleUtil;
import embasa.i18n.Localizer;
import embasa.persistence.securedb.model.DBVersion;
import embasa.persistence.securedb.service.DBVersionService;
import embasa.util.PropertiesHolder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ConfigurableApplicationContext;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DBVersionCheckerImplProductionTest {

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

    /** Версія бази даних: %s, не сумісна з мінімально допустимою версією: %s! */
    private final String WRONG_DB_VERSION_KEY = "wrongDBVersion";

    /** Текст помилки при несумісності версій. */
    private final String ERR_MESSAGE = "Версія бази даних: %s, не сумісна з мінімально допустимою версією: %s!";

    @Mock
    PropertiesHolder propertiesHolder;

    @Mock
    VersionParser versionParser;

    @Mock
    DBVersionService DBVersionService;

    @Mock
    private ConfigurableApplicationContext appContext;

    @Mock
    private Localizer localizer;

    @Test
    public void afterPropertiesSet() {
        when(propertiesHolder.get(PropertiesHolder.MIN_DB_VERSION)).thenReturn(LOWER_VERSION_STR);
        when(versionParser.parse(CHECKED_VERSION_STR, LocaleUtil.LOCALE_UA)).thenReturn(CHECKED_VERSION);
        when(versionParser.parse(LOWER_VERSION_STR, LocaleUtil.LOCALE_UA)).thenReturn(LOWER_VERSION);
        when(localizer.getMessage(WRONG_DB_VERSION_KEY)).thenReturn(ERR_MESSAGE);

        DBVersion version = new DBVersion();
        version.setVersion(CHECKED_VERSION_STR);
        when(DBVersionService.findLastByApplyDate()).thenReturn(version);

        DBVersionCheckerImplProduction versionChecker = new DBVersionCheckerImplProduction(versionParser, propertiesHolder);
        versionChecker.setDBVersionService(DBVersionService);
        versionChecker.setApplicationContext(appContext);
        versionChecker.setLocalizer(localizer);

        versionChecker.afterPropertiesSet();
        verify(appContext, times(0)).close();

        when(propertiesHolder.get(PropertiesHolder.MIN_DB_VERSION)).thenReturn(UPPER_VERSION_STR);
        when(versionParser.parse(UPPER_VERSION_STR, LocaleUtil.LOCALE_UA)).thenReturn(UPPER_VERSION);

        versionChecker = new DBVersionCheckerImplProduction(versionParser, propertiesHolder);
        versionChecker.setDBVersionService(DBVersionService);
        versionChecker.setApplicationContext(appContext);
        versionChecker.setLocalizer(localizer);

        versionChecker.afterPropertiesSet();
        verify(appContext, times(1)).close();
    }
}
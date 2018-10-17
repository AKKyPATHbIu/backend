package embasa.versioning;

import embasa.i18n.LocaleUtil;
import embasa.i18n.Localizer;
import embasa.persistence.securedb.service.DBVersionService;
import embasa.util.PropertiesHolder;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

/** Реалізація перевірки сумісності версій бази даних і frontend-у. */
public class DBVersionCheckerImplProduction extends VersionCheckerBase
        implements InitializingBean, DBVersionChecker, ApplicationContextAware {

    /** Логер. */
    private static Logger logger = Logger.getLogger(DBVersionCheckerImplProduction.class);

    /** Контекст додатку. */
    private ConfigurableApplicationContext appContext;

    /** Сервіс версії бд. */
    private DBVersionService DBVersionService;

    /** Локалізатор. */
    private Localizer localizer;

    /** Версія бази даних: %s, не сумісна з мінімально допустимою версією: %s! */
    private final String WRONG_DB_VERSION_KEY = "wrongDBVersion";

    /**
     * Конструктор
     * @param versionParser парсер версіїї в строковому вигляді
     * @param propertiesHolder утримувач налаштувань додатку
     */
    public DBVersionCheckerImplProduction(VersionParser versionParser, PropertiesHolder propertiesHolder) {
        super(versionParser, propertiesHolder);
    }

    @Autowired
    public void setDBVersionService(DBVersionService DBVersionService) {
        this.DBVersionService = DBVersionService;
    }

    @Autowired
    public void setLocalizer(Localizer localizer) {
        this.localizer = localizer;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        appContext = (ConfigurableApplicationContext) applicationContext;
    }

    @Override
    public String getMinVersionPropertyKey() {
        return PropertiesHolder.MIN_DB_VERSION;
    }

    @Override
    public void afterPropertiesSet() throws IllegalArgumentException {
        String version = DBVersionService.findLastByApplyDate().getVersion();
        if (!isVersionsSuitable(minVersion, versionParser.parse(version, LocaleUtil.LOCALE_UA))) {
            String wrongDBVersion = localizer.getMessage(WRONG_DB_VERSION_KEY);
            logger.error(String.format(wrongDBVersion, version, minVersion.toString()));
            appContext.close();
        }
    }
}

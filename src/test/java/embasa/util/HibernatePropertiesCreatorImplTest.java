package embasa.util;

import embasa.config.RootConfig;
import embasa.enums.DBDialect;
import embasa.connection.ConnectionConfig;
import embasa.connection.ConnectionPropertiesTransformer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Properties;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class,
        classes = {RootConfig.class })
@ActiveProfiles("DEV")
public class HibernatePropertiesCreatorImplTest {

    @Autowired
    ConnectionPropertiesTransformer propsTransformer;

    /** Налаштування постгреса. */
    private Properties postresProps;

    /** Налаштування оракла. */
    private Properties oracleProps;

    private final String TEST_USERNAME = "username";

    private final String TEST_PASSWORD = "password";

    private final String TEST_URL = "dev03.skydigitallab.com:5432/securedb";

    @Before
    public void prepareData() {
        Properties def = new Properties();
        def.setProperty(ConnectionPropertiesTransformer.CONNECTION_URL, TEST_URL);
        def.setProperty(ConnectionPropertiesTransformer.CONNECTION_USERNAME, TEST_USERNAME);
        def.setProperty(ConnectionPropertiesTransformer.CONNECTION_PASSWORD, TEST_PASSWORD);

        postresProps = new Properties(def);
        postresProps.setProperty(ConnectionPropertiesTransformer.CONNECTION_DIALECT, "POSTGRESQL");

        oracleProps = new Properties(def);
        oracleProps.setProperty(ConnectionPropertiesTransformer.CONNECTION_DIALECT, "MYSQL");
    }

    @Test
    public void testCreateHibernatePostgres() {
        assertFalse(postresProps.isEmpty());
        DBDialect pDialect = DBDialect.POSTGRESQL;
        ConnectionConfig pProps = propsTransformer.transform(postresProps);
        assertNotNull(pProps.getDriver());
        testProps(pProps, pDialect);
    }

    @Test
    public void testCreateHibernateOracle() {
        assertFalse(oracleProps.isEmpty());
        DBDialect oDialect = DBDialect.MYSQL;
        ConnectionConfig oProps = propsTransformer.transform(oracleProps);
        assertNotNull(oProps.getDriver());
        testProps(oProps, oDialect);
    }

    /**
     * перевірити правельніть параметрів конекта
     * @param props параметри конекта
     * @param dialect перевіряємий діалект
     */
    private void testProps(ConnectionConfig props, DBDialect dialect) {
        assertEquals(dialect.getDialect(), props.getDialect());
        assertEquals(dialect.getDriver(), props.getDriver());
        assertEquals(dialect.getUrlPrefix() + TEST_URL, props.getUrl());
        assertEquals(TEST_USERNAME, props.getUsername());
        assertEquals(TEST_PASSWORD, props.getPassword());
    }
}
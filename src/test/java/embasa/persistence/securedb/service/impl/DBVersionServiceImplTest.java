package embasa.persistence.securedb.service.impl;

import embasa.config.RootConfig;
import embasa.persistence.securedb.model.DBVersion;
import embasa.persistence.securedb.service.DBVersionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class,
        classes = {RootConfig.class })
@ActiveProfiles("DEV")
public class DBVersionServiceImplTest {

    @Autowired
    @Qualifier("secureDBDBVersionService")
    DBVersionService dbVersionService;

    @Autowired
    @Qualifier(value = "secureDBJdbcTemplate")
    JdbcTemplate jdbcTemplate;

    @Test
    public void test() {
        List<DBVersion> versions = dbVersionService.findAll();
        Integer count = jdbcTemplate.queryForObject("SELECT count(version) FROM sys_versions", Integer.class);
        assertEquals(count, (Integer) versions.size());

        String lastVersion = jdbcTemplate.queryForObject(
                "SELECT version FROM sys_versions ORDER BY apply_dt DESC LIMIT 1" ,String.class);
        assertEquals(lastVersion, dbVersionService.findLastByApplyDate().getVersion());
        DBVersion version = dbVersionService.findByVersion(lastVersion);
        assertNotNull(version);
        assertEquals(lastVersion, version.getVersion());
    }
}
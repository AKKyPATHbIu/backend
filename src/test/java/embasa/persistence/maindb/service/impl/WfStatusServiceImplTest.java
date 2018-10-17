package embasa.persistence.maindb.service.impl;

import embasa.config.RootConfig;
import embasa.persistence.maindb.model.WfStatus;
import embasa.persistence.maindb.service.WfStatusService;
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
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class,
        classes = {RootConfig.class })
@ActiveProfiles("DEV")
public class WfStatusServiceImplTest {

    @Autowired
    @Qualifier(value = "mainDBTransactionManager")
    PlatformTransactionManager txManager;

    @Autowired
    @Qualifier(value = "mainDBJdbcTemplate")
    JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier(value = "mainDBWfStatusService")
    WfStatusService statusService;

    @Test
    public void test() {
        assertNotNull(statusService);

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);

        Long clinicId = jdbcTemplate.queryForObject("SELECT id FROM clinics LIMIT 1", Long.class);

        try {
            WfStatus s = new WfStatus();
            s.setClinicId(clinicId);
            s.setName("test_name");
            s.setDescr("test_descr");

            statusService.save(s);
            Long id = s.getId();
            assertNotNull(id);

            assertTrue(statusService.isExist(id));

            WfStatus s1 = statusService.findById(id);
            assertNotNull(s1);

            s.setName("new_test_name");
            s.setDescr("new_test_descr");

            statusService.update(s);

            s1 = statusService.findById(id);
            assertEquals(s, s1);
            statusService.delete(id);
            assertFalse(statusService.isExist(id));
        } catch (Exception ex) {
            ex.printStackTrace();
            fail();
        } finally {
            txManager.rollback(status);
        }
    }
}
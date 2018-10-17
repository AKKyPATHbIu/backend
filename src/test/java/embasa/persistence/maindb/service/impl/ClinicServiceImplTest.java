package embasa.persistence.maindb.service.impl;

import embasa.config.RootConfig;
import embasa.persistence.maindb.model.Clinic;
import embasa.persistence.maindb.service.ClinicService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class,
        classes = {RootConfig.class })
@ActiveProfiles("DEV")
@Ignore
public class ClinicServiceImplTest {

    @Autowired
    @Qualifier(value = "mainDBTransactionManager")
    PlatformTransactionManager txManager;

    @Autowired
    @Qualifier("mainDBClinicService")
    ClinicService clinicService;

    private static final String CLINIC_NAME = "_test_clinic_name_";
    private static final String CLINIC_DESCR = "_test_clinic_descr_";
    private static final Long CLINIC_STATUS = 333L;

    private static final String CLINIC_UPDATED_NAME = "_test_clinic_name_updated_";
    private static final String CLINIC_UPDATED_DESCR = "_test_clinic_descr_updated_";
    private static final Long CLINIC_UPDATED_STATUS = 1L;

    @Test
    public void test() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(def);

        try {
            Clinic c = new Clinic();
            c.setName(CLINIC_NAME);
            c.setDescription(CLINIC_DESCR);
            c.setStatus(CLINIC_STATUS);

            clinicService.save(c);
            Long clinicId = c.getId();
            assertNotNull(clinicId);
            assertTrue(clinicService.isExist(clinicId));

            Clinic dbClinic = clinicService.findById(clinicId);
            assertNotNull(dbClinic);
            assertEquals(CLINIC_NAME, dbClinic.getName());
            assertEquals(CLINIC_DESCR, dbClinic.getDescription());
            assertEquals(CLINIC_STATUS, dbClinic.getStatus());
            assertEquals(c, dbClinic);

            List<Clinic> clinics = clinicService.findAll();
            assertFalse(clinics.isEmpty());
            assertTrue(clinics.contains(dbClinic));

            c.setName(CLINIC_UPDATED_NAME);
            c.setDescription(CLINIC_UPDATED_DESCR);
            c.setStatus(CLINIC_UPDATED_STATUS);
            clinicService.update(c);

            dbClinic = clinicService.findById(clinicId);
            assertEquals(CLINIC_UPDATED_NAME, dbClinic.getName());
            assertEquals(CLINIC_UPDATED_DESCR, dbClinic.getDescription());
            assertEquals(CLINIC_UPDATED_STATUS, dbClinic.getStatus());
            assertEquals(c, dbClinic);

            clinicService.delete(clinicId);
            assertFalse(clinicService.isExist(clinicId));
        } catch(Exception ex) {
            ex.printStackTrace();
            fail();
        } finally {
            txManager.rollback(txStatus);
        }
    }
}
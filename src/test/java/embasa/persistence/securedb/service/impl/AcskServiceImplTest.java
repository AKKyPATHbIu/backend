package embasa.persistence.securedb.service.impl;

import embasa.config.RootConfig;
import embasa.i18n.LanguageHolder;
import embasa.persistence.common.service.MsgValueService;
import embasa.persistence.maindb.service.impl.ServiceUtil;
import embasa.persistence.securedb.model.Acsk;
import embasa.persistence.securedb.service.AcskService;
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
public class AcskServiceImplTest {

    @Autowired
    @Qualifier("secureDBAcskService")
    AcskService acskService;

    @Autowired
    @Qualifier(value = "secureDBTransactionManager")
    PlatformTransactionManager txManager;

    @Autowired
    @Qualifier("secureDBMsgValueService")
    MsgValueService msgValueService;

    @Autowired
    @Qualifier(value = "secureDBLanguageHolder")
    LanguageHolder languageHolder;

    static final String ACSK_RECOURCE_CODE = "_test_acsk_code_";
    static final String ACSK_RECOURCE_CODE_1 = ACSK_RECOURCE_CODE + "1_";

    @Test
    public void test() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(def);
        try {
            assertTrue(!acskService.findAll().isEmpty());
            ServiceUtil.createLocalizeResources(ACSK_RECOURCE_CODE, msgValueService);
            ServiceUtil.createLocalizeResources(ACSK_RECOURCE_CODE_1, msgValueService);
            assertTrue(msgValueService.isExists(ACSK_RECOURCE_CODE));
            assertTrue(msgValueService.isExists(ACSK_RECOURCE_CODE_1));

            Acsk acsk = new Acsk();
            acsk.setNameCode(ACSK_RECOURCE_CODE);
            acsk.setCmpPort(100);
            acsk.setCmpAddress("test.com");

            acskService.save(acsk);
            Long id = acsk.getId();
            assertNotNull(id);

            Acsk dbAcsk = acskService.findById(id);
            assertNotNull(dbAcsk);

            assertEquals(acsk.getCmpAddress(), dbAcsk.getCmpAddress());
            assertEquals(acsk.getCmpPort(), dbAcsk.getCmpPort());
            assertEquals(acsk.getNameCode(), dbAcsk.getNameCode());

            assertEquals(ServiceUtil.VALUE_UA, dbAcsk.getNameResource(languageHolder.getLanguageBy(ServiceUtil.LANG_CODE_UA)));
            assertEquals(ServiceUtil.VALUE_RU, dbAcsk.getNameResource(languageHolder.getLanguageBy(ServiceUtil.LANG_CODE_RU)));
            assertEquals(ServiceUtil.VALUE_EN, dbAcsk.getNameResource(languageHolder.getLanguageBy(ServiceUtil.LANG_CODE_EN)));

            List<Acsk> acsks = acskService.findAll();
            assertTrue(acsks.contains(dbAcsk));

            dbAcsk.setNameCode(ACSK_RECOURCE_CODE_1);
            dbAcsk.setCmpAddress("new_address.com");
            dbAcsk.setCmpPort(8080);

            acskService.update(dbAcsk);

            acsk = acskService.findById(id);

            assertEquals(dbAcsk.getCmpAddress(), acsk.getCmpAddress());
            assertEquals(dbAcsk.getCmpPort(), acsk.getCmpPort());
            assertEquals(dbAcsk.getNameCode(), acsk.getNameCode());

            acskService.delete(id);
            assertFalse(acskService.isExist(id));
        } finally {
            txManager.rollback(txStatus);
        }
    }
}
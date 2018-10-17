package embasa.persistence.common.service.impl;

import embasa.config.RootConfig;
import embasa.persistence.common.model.MsgValue;
import embasa.persistence.common.service.MsgValueService;
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
public class MsgValueServiceImplTest {

    @Autowired
    @Qualifier(value = "mainDBMsgValueService")
    MsgValueService mainDBService;

    @Autowired
    @Qualifier(value = "secureDBMsgValueService")
    MsgValueService secureDBService;

    @Autowired
    @Qualifier(value = "mainDBTransactionManager")
    PlatformTransactionManager txManager;

    private final String LANG_CODE_UA = "UA";
    private final String LANG_CODE_RU = "RU";
    private final String LANG_CODE_EN = "EN";

    @Test
    public void test() {
        String testCode = "_test_resource_code_";

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);

        try {
            assertFalse(mainDBService.isExists(testCode));
            assertFalse(secureDBService.isExists(testCode));

            MsgValue msgValueUA = new MsgValue();
            msgValueUA.setCode(testCode);
            msgValueUA.setLangCode(LANG_CODE_UA);
            msgValueUA.setValue("українська");

            mainDBService.save(msgValueUA);
            assertTrue(mainDBService.isExists(testCode));
            assertFalse(secureDBService.isExists(testCode));

            MsgValue msgValueRU = new MsgValue();
            msgValueRU.setCode(testCode);
            msgValueRU.setLangCode(LANG_CODE_RU);
            msgValueRU.setValue("русский");
            mainDBService.save(msgValueRU);

            List<MsgValue> values = mainDBService.findByCode(testCode);
            assertTrue(values.size() == 2);
            assertTrue(values.contains(msgValueUA));
            assertTrue(values.contains(msgValueRU));

            MsgValue dbUA = mainDBService.findByCodeAndLang(testCode, LANG_CODE_UA);
            assertNotNull(dbUA);
            assertEquals(msgValueUA, dbUA);
            MsgValue dbRU = mainDBService.findByCodeAndLang(testCode, LANG_CODE_RU);
            assertNotNull(dbRU);
            assertEquals(msgValueRU, dbRU);

            MsgValue msgValueEN = new MsgValue();
            msgValueEN.setCode(testCode);
            msgValueEN.setLangCode(LANG_CODE_EN);
            msgValueEN.setValue("English");
            secureDBService.save(msgValueEN);

            assertTrue(secureDBService.isExists(testCode));

            dbUA.setValue("Нове значення");
            mainDBService.update(dbUA);

            MsgValue updated = mainDBService.findByCodeAndLang(testCode, LANG_CODE_UA);
            assertEquals(dbUA, updated);
        } catch (Exception ex) {
            fail();
            ex.printStackTrace();
        } finally {
            mainDBService.delete(testCode);
            secureDBService.delete(testCode);
            assertFalse(mainDBService.isExists(testCode));
            assertFalse(secureDBService.isExists(testCode));
            txManager.rollback(status);
        }
    }
}
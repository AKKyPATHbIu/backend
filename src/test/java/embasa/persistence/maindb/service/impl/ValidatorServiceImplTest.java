package embasa.persistence.maindb.service.impl;

import embasa.config.RootConfig;
import embasa.persistence.common.model.MsgValue;
import embasa.persistence.common.service.MsgValueService;
import embasa.persistence.maindb.model.Validator;
import embasa.persistence.maindb.service.ValidatorService;
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
public class ValidatorServiceImplTest {

    @Autowired
    @Qualifier(value = "mainDBValidatorService")
    ValidatorService validatorService;

    @Autowired
    @Qualifier(value = "mainDBTransactionManager")
    PlatformTransactionManager txManager;

    @Autowired
    @Qualifier(value = "mainDBMsgValueService")
    MsgValueService valueService;

    private final String LANG_CODE_UA = "UA";
    private final String LANG_CODE_RU = "RU";
    private final String LANG_CODE_EN = "EN";

    private String testCode = "_validator_test_";

    @Test
    public void test() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);
        Long id = null;
        try {
            valueService.delete(testCode);

            MsgValue msgValueUA = new MsgValue();
            msgValueUA.setCode(testCode);
            msgValueUA.setLangCode(LANG_CODE_UA);
            msgValueUA.setValue("українська");
            valueService.save(msgValueUA);

            MsgValue msgValueRU = new MsgValue();
            msgValueRU.setCode(testCode);
            msgValueRU.setLangCode(LANG_CODE_RU);
            msgValueRU.setValue("русский");
            valueService.save(msgValueRU);

            Validator v = new Validator();
            v.setNameCode(testCode);
            v.setDataTypeId(2L);
            v.setRule("rule");

            validatorService.save(v);

            id = v.getId();
            assertNotNull(id);
            validatorService.isExist(id);
            Validator dbV = validatorService.findById(id);

            assertEquals(v, dbV);
            List<Validator> validators = validatorService.findAll();
            assertTrue(validators.contains(dbV));
        } catch (Exception ex) {
            ex.printStackTrace();
            fail();
        } finally {
            txManager.rollback(status);
            if (id != null) {
                validatorService.delete(id);
                assertFalse(validatorService.isExist(id));
            }
            valueService.delete(testCode);
        }
    }
}
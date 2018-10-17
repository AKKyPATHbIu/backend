package embasa.persistence.maindb.service.impl;

import embasa.config.RootConfig;
import embasa.persistence.common.model.MsgValue;
import embasa.persistence.common.service.MsgValueService;
import embasa.persistence.maindb.model.Module;
import embasa.persistence.maindb.service.ModuleService;
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
public class ModuleServiceImplTest {

    @Autowired
    @Qualifier(value = "mainDBModuleService")
    ModuleService moduleSevice;

    @Autowired
    @Qualifier(value = "mainDBTransactionManager")
    PlatformTransactionManager txManager;

    @Autowired
    @Qualifier(value = "mainDBMsgValueService")
    MsgValueService valueService;

    private static String NAME_CODE = "_module_name_test_";
    private static String DESC_CODE = "_module_descr_test_";

    @Test
    public void test() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);
        try {
            ServiceUtil.createLocalizeResources(NAME_CODE, valueService);
            ServiceUtil.createLocalizeResources(DESC_CODE, valueService);

            Module m = new Module();
            m.getName().setCode(NAME_CODE);
            m.getDescr().setCode(DESC_CODE);

            moduleSevice.save(m);

            Module dbModule = moduleSevice.findById(m.getId());
            assertNotNull(dbModule);

            assertEquals(3, dbModule.getName().size());

            MsgValue msgNameUA = valueService.findByCodeAndLang(NAME_CODE, ServiceUtil.LANG_CODE_UA);
            MsgValue msgNameRU = valueService.findByCodeAndLang(NAME_CODE, ServiceUtil.LANG_CODE_RU);
            MsgValue msgNameEN = valueService.findByCodeAndLang(NAME_CODE, ServiceUtil.LANG_CODE_EN);

            dbModule.getName().contains(msgNameUA);
            dbModule.getName().contains(msgNameRU);
            dbModule.getName().contains(msgNameEN);

            assertEquals(3, dbModule.getDescr().size());

            MsgValue msgDescrUA = valueService.findByCodeAndLang(DESC_CODE, ServiceUtil.LANG_CODE_UA);
            MsgValue msgDescrRU = valueService.findByCodeAndLang(DESC_CODE, ServiceUtil.LANG_CODE_RU);
            MsgValue msgDescrEN = valueService.findByCodeAndLang(DESC_CODE, ServiceUtil.LANG_CODE_EN);

            dbModule.getDescr().contains(msgDescrUA);
            dbModule.getDescr().contains(msgDescrRU);
            dbModule.getDescr().contains(msgDescrEN);

            List<Module> modules = moduleSevice.findAll();
            assertTrue(!modules.isEmpty());
        } catch (Exception ex) {
            ex.printStackTrace();
            fail();
        } finally {
            txManager.rollback(status);
        }
    }
}
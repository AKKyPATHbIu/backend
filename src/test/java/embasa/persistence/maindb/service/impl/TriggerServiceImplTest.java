package embasa.persistence.maindb.service.impl;

import embasa.config.RootConfig;
import embasa.i18n.LanguageHolder;
import embasa.i18n.LocaleUtil;
import embasa.persistence.common.LocalizedList;
import embasa.persistence.common.model.MsgValue;
import embasa.persistence.common.service.MsgValueService;
import embasa.persistence.maindb.model.Trigger;
import embasa.persistence.maindb.service.TriggerService;
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
public class TriggerServiceImplTest {

    @Autowired
    TriggerService triggerService;

    @Autowired
    @Qualifier(value = "mainDBMsgValueService")
    MsgValueService valueService;

    @Autowired
    @Qualifier(value = "mainDBLanguageHolder")
    LanguageHolder languageHolder;

    @Autowired
    @Qualifier(value = "mainDBTransactionManager")
    PlatformTransactionManager txManager;

    private static String NAME_CODE = "_trigger_name_code_";
    private static String DESC_CODE = "_trigger_descr_code_";

    @Test
    public void test() {
        assertNotNull(triggerService);

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);

        try {
            ServiceUtil.createLocalizeResources(NAME_CODE, valueService);
            ServiceUtil.createLocalizeResources(DESC_CODE, valueService);

            MsgValue msgNameUA = valueService.findByCodeAndLang(NAME_CODE, ServiceUtil.LANG_CODE_UA);
            MsgValue msgNameRU = valueService.findByCodeAndLang(NAME_CODE, ServiceUtil.LANG_CODE_RU);
            MsgValue msgNameEN = valueService.findByCodeAndLang(NAME_CODE, ServiceUtil.LANG_CODE_EN);

            assertNotNull(msgNameUA);
            assertNotNull(msgNameRU);
            assertNotNull(msgNameEN);

            MsgValue msgDescrUA = valueService.findByCodeAndLang(DESC_CODE, ServiceUtil.LANG_CODE_UA);
            MsgValue msgDescrRU = valueService.findByCodeAndLang(DESC_CODE, ServiceUtil.LANG_CODE_RU);
            MsgValue msgDescrEN = valueService.findByCodeAndLang(DESC_CODE, ServiceUtil.LANG_CODE_EN);

            assertNotNull(msgDescrUA);
            assertNotNull(msgDescrRU);
            assertNotNull(msgDescrEN);

            Long id = null;

            try {
                Trigger trigger = new Trigger();
                trigger.setModuleId(2L);
                trigger.getName().setCode(NAME_CODE);
                trigger.getDescr().setCode(DESC_CODE);

                triggerService.save(trigger);

                id = trigger.getId();
                assertNotNull(id);
                assertTrue(triggerService.isExist(id));

                Trigger t = triggerService.findById(id);
                assertEquals(t, trigger);

                List<Trigger> list = triggerService.findAll();
                assertTrue(list.contains(t));
                assertTrue(list.contains(trigger));

                LocalizedList nameList = t.getName();
                assertEquals(3, nameList.size());
                assertTrue(nameList.contains(LocaleUtil.getLocalized(ServiceUtil.LANG_CODE_UA, msgNameUA.getValue(), languageHolder)));
                assertTrue(nameList.contains(LocaleUtil.getLocalized(ServiceUtil.LANG_CODE_RU, msgNameRU.getValue(), languageHolder)));
                assertTrue(nameList.contains(LocaleUtil.getLocalized(ServiceUtil.LANG_CODE_EN, msgNameEN.getValue(), languageHolder)));

                LocalizedList descrList = t.getDescr();
                assertEquals(3, descrList.size());
                assertTrue(descrList.contains(LocaleUtil.getLocalized(ServiceUtil.LANG_CODE_UA, msgDescrUA.getValue(), languageHolder)));
                assertTrue(descrList.contains(LocaleUtil.getLocalized(ServiceUtil.LANG_CODE_RU, msgDescrRU.getValue(), languageHolder)));
                assertTrue(descrList.contains(LocaleUtil.getLocalized(ServiceUtil.LANG_CODE_EN, msgDescrEN.getValue(), languageHolder)));
            } catch (Exception ex) {
                ex.printStackTrace();
                fail();
            } finally {
                valueService.delete(NAME_CODE);
                valueService.delete(DESC_CODE);
                if (id != null) {
                    triggerService.delete(id);
                    assertFalse(triggerService.isExist(id));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            fail();
        } finally {
            txManager.rollback(status);
        }
    }
}
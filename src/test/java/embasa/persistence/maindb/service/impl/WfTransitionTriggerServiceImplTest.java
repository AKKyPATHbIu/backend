package embasa.persistence.maindb.service.impl;

import embasa.config.RootConfig;
import embasa.i18n.LanguageHolder;
import embasa.persistence.common.service.MsgValueService;
import embasa.persistence.maindb.model.CardEntity;
import embasa.persistence.maindb.model.Trigger;
import embasa.persistence.maindb.model.WfStatus;
import embasa.persistence.maindb.model.WfTransitionTrigger;
import embasa.persistence.maindb.service.CardEntityService;
import embasa.persistence.maindb.service.TriggerService;
import embasa.persistence.maindb.service.WfStatusService;
import embasa.persistence.maindb.service.WfTransitionTriggerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class,
        classes = {RootConfig.class })
@ActiveProfiles("DEV")
public class WfTransitionTriggerServiceImplTest {

    @Autowired
    @Qualifier(value = "mainDBTransactionManager")
    PlatformTransactionManager txManager;

    @Autowired
    @Qualifier(value = "mainDBJdbcTemplate")
    JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier(value = "mainDBCardEntityService")
    CardEntityService entityService;

    @Autowired
    @Qualifier(value = "mainDBWfStatusService")
    WfStatusService statusService;

    @Autowired
    @Qualifier(value = "mainDBTriggerService")
    TriggerService triggerService;

    @Autowired
    @Qualifier(value = "mainDBMsgValueService")
    MsgValueService valueService;

    @Autowired
    @Qualifier(value = "mainDBWfTransitionTriggerService")
    WfTransitionTriggerService trTriggerService;

    @Autowired
    @Qualifier(value = "mainDBLanguageHolder")
    LanguageHolder languageHolder;

    private static String TR_NAME_CODE_1 = "_trigger_name_1_";
    private static String TR_DESC_CODE_1 = "_trigger_descr_1_";

    private static String TR_NAME_CODE_2 = "_trigger_name_2_";
    private static String TR_DESC_CODE_2 = "_trigger_descr_2_";

    @Test
    public void test() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(def);

        Long clinicId = jdbcTemplate.queryForObject("SELECT id FROM clinics LIMIT 1", Long.class);
        Long moduleId = jdbcTemplate.queryForObject("SELECT id FROM modules LIMIT 1", Long.class);
        try {
            CardEntity entity = new CardEntity();
            entity.setClinicId(clinicId);
            entity.setSystem(true);
            entity.setModuleId(moduleId);
            entity.setName("test_entity");
            entityService.save(entity);
            assertNotNull(entity.getId());

            WfStatus status = new WfStatus();
            status.setName("test_status");
            status.setClinicId(clinicId);
            statusService.save(status);
            assertNotNull(status.getId());

            WfStatus nextStatus = new WfStatus();
            nextStatus.setName("next_status");
            nextStatus.setClinicId(clinicId);
            statusService.save(nextStatus);
            assertNotNull(nextStatus.getId());

            SimpleJdbcInsert jdbcInsertTransitions = new SimpleJdbcInsert(jdbcTemplate).withTableName("wf_transitions");
            jdbcInsertTransitions.setGeneratedKeyName("id");
            Map<String, Object> params = new HashMap<>();
            params.put("entity_id", entity.getId());
            params.put("status_id", status.getId());
            params.put("next_status_id", nextStatus.getId());
            params.put("level", 1);
            Long transitionId = jdbcInsertTransitions.executeAndReturnKey(params).longValue();
            assertNotNull(transitionId);

            ServiceUtil.createLocalizeResources(TR_NAME_CODE_1, valueService);
            ServiceUtil.createLocalizeResources(TR_DESC_CODE_1, valueService);

            ServiceUtil.createLocalizeResources(TR_NAME_CODE_2, valueService);
            ServiceUtil.createLocalizeResources(TR_DESC_CODE_2, valueService);

            Trigger trigger1 = new Trigger();
            trigger1.setModuleId(moduleId);
            trigger1.getName().setCode(TR_NAME_CODE_1);
            trigger1.getDescr().setCode(TR_DESC_CODE_1);
            triggerService.save(trigger1);
            assertNotNull(trigger1.getId());

            Trigger trigger2 = new Trigger();
            trigger2.setModuleId(moduleId);
            trigger2.getName().setCode(TR_NAME_CODE_2);
            trigger2.getDescr().setCode(TR_DESC_CODE_2);
            triggerService.save(trigger2);
            assertNotNull(trigger2.getId());

            WfTransitionTrigger trTrigger1 = new WfTransitionTrigger();
            trTrigger1.setTransitionId(transitionId);
            trTrigger1.setTrigger(trigger1);
            trTrigger1.setParams("{\"param\" : 1}");
            trTriggerService.save(trTrigger1);

            WfTransitionTrigger trTrigger2 = new WfTransitionTrigger();
            trTrigger2.setTransitionId(transitionId);
            trTrigger2.setTrigger(trigger2);
            trTrigger2.setParams("{\"param\" : 2}");
            trTriggerService.save(trTrigger2);

            List<WfTransitionTrigger> trTriggers = trTriggerService.findByTransition(transitionId);
            assertEquals(2, trTriggers.size());

            for (WfTransitionTrigger trTrigger : trTriggers) {
                assertEquals(trTrigger.getTransitionId(), transitionId);

                Trigger trigger = trTrigger.getTrigger();
                if (trigger.equals(trigger1)) {
                    assertEquals("{\"param\" : 1}", trTrigger.getParams());
                } else if (trigger.equals(trigger2)) {
                    assertEquals("{\"param\" : 2}", trTrigger.getParams());
                } else {
                    fail();
                }

                assertEquals(3, trigger.getName().size());
                assertEquals(ServiceUtil.VALUE_UA, trigger.getName().getResource(
                        languageHolder.getLanguageBy(ServiceUtil.LANG_CODE_UA)));
                assertEquals(ServiceUtil.VALUE_RU, trigger.getName().getResource(
                        languageHolder.getLanguageBy(ServiceUtil.LANG_CODE_RU)));
                assertEquals(ServiceUtil.VALUE_EN, trigger.getName().getResource(
                        languageHolder.getLanguageBy(ServiceUtil.LANG_CODE_EN)));

                assertEquals(3, trigger.getDescr().size());
                assertEquals(ServiceUtil.VALUE_UA, trigger.getDescr().getResource(
                        languageHolder.getLanguageBy(ServiceUtil.LANG_CODE_UA)));
                assertEquals(ServiceUtil.VALUE_RU, trigger.getDescr().getResource(
                        languageHolder.getLanguageBy(ServiceUtil.LANG_CODE_RU)));
                assertEquals(ServiceUtil.VALUE_EN, trigger.getDescr().getResource(
                        languageHolder.getLanguageBy(ServiceUtil.LANG_CODE_EN)));
            }
        } catch(Exception ex) {
            ex.printStackTrace();
            fail();
        } finally {
            txManager.rollback(txStatus);
        }
    }
}
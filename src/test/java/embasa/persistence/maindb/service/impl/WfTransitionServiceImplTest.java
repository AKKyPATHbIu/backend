package embasa.persistence.maindb.service.impl;

import embasa.config.RootConfig;
import embasa.persistence.common.service.MsgValueService;
import embasa.persistence.maindb.model.*;
import embasa.persistence.maindb.service.*;
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

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class,
        classes = {RootConfig.class })
@ActiveProfiles("DEV")
public class WfTransitionServiceImplTest {

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
    @Qualifier(value = "mainDBMsgValueService")
    MsgValueService valueService;

    @Autowired
    @Qualifier(value = "mainDBValidatorService")
    ValidatorService validatorService;

    @Autowired
    @Qualifier(value = "mainDBTriggerService")
    TriggerService triggerService;

    @Autowired
    @Qualifier(value = "mainDBWfTransitionService")
    WfTransitionService transitionService;

    @Autowired
    @Qualifier(value = "mainDBWfTransitionTriggerService")
    WfTransitionTriggerService transitionTriggerService;

    @Autowired
    @Qualifier(value = "mainDBWfTransitionValidatorService")
    WfTransitionValidatorService transitionValidatorService;

    private static String TR_NAME_CODE_1 = "_trigger_name_1_";
    private static String TR_DESC_CODE_1 = "_trigger_descr_1_";

    private static String TR_NAME_CODE_2 = "_trigger_name_2_";
    private static String TR_DESC_CODE_2 = "_trigger_descr_2_";

    private static String VL_NAME_CODE = "_validator_name_1_";
    private static String VL_DESC_CODE = "_validator_descr_1_";

    @Test
    public void test() {
        //SELECT wf_trans_add(351,200,201,null,ARRAY[(0,136,'{"param" : 2}')]::wf_transition_validators[],ARRAY[(0,167,'{"ppp" : "100"}')]::wf_transition_triggers[])
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(def);

        Long clinicId = jdbcTemplate.queryForObject("SELECT id FROM clinics LIMIT 1", Long.class);
        Long moduleId = jdbcTemplate.queryForObject("SELECT id FROM modules LIMIT 1", Long.class);
        Long dataTypeId = jdbcTemplate.queryForObject("SELECT id FROM entity_data_types LIMIT 1", Long.class);

        try {
            CardEntity entity = new CardEntity();
            entity.setName("entity");
            entity.setModuleId(moduleId);
            entity.setSystem(true);
            entity.setClinicId(clinicId);

            entityService.save(entity);
            assertNotNull(entity.getId());

            ServiceUtil.createLocalizeResources(TR_NAME_CODE_1, valueService);
            ServiceUtil.createLocalizeResources(TR_DESC_CODE_1, valueService);
            ServiceUtil.createLocalizeResources(TR_NAME_CODE_2, valueService);
            ServiceUtil.createLocalizeResources(TR_DESC_CODE_2, valueService);
            ServiceUtil.createLocalizeResources(VL_NAME_CODE, valueService);
            ServiceUtil.createLocalizeResources(VL_DESC_CODE, valueService);

            Validator v = new Validator();
            v.setDataTypeId(dataTypeId);
            v.setNameCode(VL_NAME_CODE);
            v.setDescCode(VL_DESC_CODE);
            validatorService.save(v);
            assertNotNull(v.getId());

            Trigger t1 = new Trigger();
            t1.setModuleId(moduleId);
            t1.setDescCode(TR_DESC_CODE_1);
            t1.setNameCode(TR_NAME_CODE_1);
            triggerService.save(t1);
            assertNotNull(t1.getId());

            Trigger t2 = new Trigger();
            t2.setModuleId(moduleId);
            t2.setDescCode(TR_DESC_CODE_2);
            t2.setNameCode(TR_NAME_CODE_2);
            triggerService.save(t2);
            assertNotNull(t2.getId());

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

            WfTransition transition = new WfTransition();
            transition.setEntityId(entity.getId());
            transition.setStatusId(status.getId());
            transition.setNextStatusId(nextStatus.getId());

            WfTransitionTrigger trTrigger = new WfTransitionTrigger();
            trTrigger.setParams("{\"ppp\" : \"100\"}");
            trTrigger.setTrigger(t1);
            transition.addTrigger(trTrigger);

            WfTransitionTrigger trTrigger2 = new WfTransitionTrigger();
            trTrigger2.setParams("{\"ttt\" : \"aaa\"}");
            trTrigger2.setTrigger(t2);
            transition.addTrigger(trTrigger2);

            WfTransitionValidator trValidator = new WfTransitionValidator();
            trValidator.setParams("{\"param\" : 2}");
            trValidator.setValidator(v);
            transition.addValidator(trValidator);

            transitionService.save(transition);
            Long transitionId = transition.getId();
            assertNotNull(transitionId);

            assertTrue(transitionService.isExist(transitionId));
            WfTransition dbTr = transitionService.findById(transitionId);
            assertNotNull(dbTr);
            assertEquals(transition, dbTr);

            List<WfTransitionTrigger> trTriggers = transitionTriggerService.findByTransition(transitionId);
            assertEquals(2, trTriggers.size());
            assertTrue(trTriggers.contains(trTrigger));
            assertTrue(trTriggers.contains(trTrigger2));

            List<WfTransitionValidator> trValidators = transitionValidatorService.findByTransition(transitionId);
            assertEquals(1, trValidators.size());
            assertTrue(trValidators.contains(trValidator));

            WfTransition transition1 = new WfTransition();
            transition1.setEntityId(entity.getId());
            transition1.setStatusId(status.getId());
            transition1.setNextStatusId(nextStatus.getId());
            transitionService.save(transition1);
            Long trId1 = transition1.getId();
            assertNotNull(trId1);
            assertTrue(transitionTriggerService.findByTransition(trId1).isEmpty());
            assertTrue(transitionValidatorService.findByTransition(trId1).isEmpty());

            transition1.setLevel(500);
            transitionService.update(transition1);
            WfTransition dbTransition = transitionService.findById(trId1);
            assertEquals(transition1.getLevel(), dbTransition.getLevel());

            transitionService.delete(trId1);
            assertFalse(transitionService.isExist(trId1));
        } catch(Exception ex) {
            ex.printStackTrace();
            fail();
        } finally {
            txManager.rollback(txStatus);
        }
    }
}
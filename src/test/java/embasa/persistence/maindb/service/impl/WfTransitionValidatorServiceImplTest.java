package embasa.persistence.maindb.service.impl;

import embasa.config.RootConfig;
import embasa.i18n.LanguageHolder;
import embasa.persistence.common.service.MsgValueService;
import embasa.persistence.maindb.model.CardEntity;
import embasa.persistence.maindb.model.Validator;
import embasa.persistence.maindb.model.WfStatus;
import embasa.persistence.maindb.model.WfTransitionValidator;
import embasa.persistence.maindb.service.CardEntityService;
import embasa.persistence.maindb.service.ValidatorService;
import embasa.persistence.maindb.service.WfStatusService;
import embasa.persistence.maindb.service.WfTransitionValidatorService;
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

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class,
        classes = {RootConfig.class })
@ActiveProfiles("DEV")
public class WfTransitionValidatorServiceImplTest {

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
    @Qualifier(value = "mainDBValidatorService")
    ValidatorService validatorService;

    @Autowired
    @Qualifier(value = "mainDBMsgValueService")
    MsgValueService valueService;

    @Autowired
    @Qualifier(value = "mainDBWfTransitionValidatorService")
    WfTransitionValidatorService trValidatorService;

    @Autowired
    @Qualifier(value = "mainDBLanguageHolder")
    LanguageHolder languageHolder;

    private static String V_NAME_CODE_1 = "_trigger_name_1_";
    private static String V_DESC_CODE_1 = "_trigger_descr_1_";

    private static String V_NAME_CODE_2 = "_trigger_name_2_";
    private static String V_DESC_CODE_2 = "_trigger_descr_2_";

    @Test
    public void test() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(def);

        Long moduleId = jdbcTemplate.queryForObject("SELECT id FROM modules LIMIT 1", Long.class);
        Long typeId = jdbcTemplate.queryForObject("SELECT id FROM entity_data_types LIMIT 1", Long.class);
        Long clinicId = jdbcTemplate.queryForObject("SELECT id FROM clinics LIMIT 1", Long.class);
        try {
            CardEntity entity = new CardEntity();
            entity.setSystem(true);
            entity.setClinicId(clinicId);
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

            ServiceUtil.createLocalizeResources(V_NAME_CODE_1, valueService);
            ServiceUtil.createLocalizeResources(V_DESC_CODE_1, valueService);

            ServiceUtil.createLocalizeResources(V_NAME_CODE_2, valueService);
            ServiceUtil.createLocalizeResources(V_DESC_CODE_2, valueService);

            Validator validator1 = new Validator();
            validator1.setDataTypeId(typeId);
            validator1.getName().setCode(V_NAME_CODE_1);
            validator1.getDescr().setCode(V_DESC_CODE_1);
            validatorService.save(validator1);
            assertNotNull(validator1.getId());

            Validator validator2 = new Validator();
            validator2.setDataTypeId(typeId);
            validator2.getName().setCode(V_NAME_CODE_2);
            validator2.getDescr().setCode(V_DESC_CODE_2);
            validatorService.save(validator2);
            assertNotNull(validator2.getId());

            WfTransitionValidator trValidator1 = new WfTransitionValidator();
            trValidator1.setTransitionId(transitionId);
            trValidator1.setValidator(validator1);
            trValidator1.setParams("{\"param\" : 1}");
            trValidatorService.save(trValidator1);

            WfTransitionValidator trValidator2 = new WfTransitionValidator();
            trValidator2.setTransitionId(transitionId);
            trValidator2.setValidator(validator2);
            trValidator2.setParams("{\"param\" : 2}");
            trValidatorService.save(trValidator2);

            List<WfTransitionValidator> trValidators = trValidatorService.findByTransition(transitionId);
            assertEquals(2, trValidators.size());

            for (WfTransitionValidator trValidator : trValidators) {
                assertEquals(trValidator.getTransitionId(), transitionId);

                Validator validator = trValidator.getValidator();
                assertEquals(validator.equals(validator1) ? "{\"param\" : 1}" : validator.equals(validator2) ?  "{\"param\" : 2}" : null, trValidator.getParams());

                assertEquals(3, validator.getName().size());
                assertEquals(ServiceUtil.VALUE_UA, validator.getName().getResource(
                        languageHolder.getLanguageBy(ServiceUtil.LANG_CODE_UA)));
                assertEquals(ServiceUtil.VALUE_RU, validator.getName().getResource(
                        languageHolder.getLanguageBy(ServiceUtil.LANG_CODE_RU)));
                assertEquals(ServiceUtil.VALUE_EN, validator.getName().getResource(
                        languageHolder.getLanguageBy(ServiceUtil.LANG_CODE_EN)));

                assertEquals(3, validator.getDescr().size());
                assertEquals(ServiceUtil.VALUE_UA, validator.getDescr().getResource(
                        languageHolder.getLanguageBy(ServiceUtil.LANG_CODE_UA)));
                assertEquals(ServiceUtil.VALUE_RU, validator.getDescr().getResource(
                        languageHolder.getLanguageBy(ServiceUtil.LANG_CODE_RU)));
                assertEquals(ServiceUtil.VALUE_EN, validator.getDescr().getResource(
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
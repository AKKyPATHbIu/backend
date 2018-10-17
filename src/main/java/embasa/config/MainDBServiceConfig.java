package embasa.config;

import embasa.persistence.common.repository.MsgValueRepository;
import embasa.persistence.common.service.LanguageService;
import embasa.persistence.common.service.MsgValueService;
import embasa.persistence.maindb.service.*;
import embasa.persistence.maindb.service.impl.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainDBServiceConfig {

    @Bean(name = "mainDBClinicService")
    public ClinicService clinicService() {
        return new ClinicServiceImpl();
    }

    @Bean(name = "mainDBCardEntityService")
    public CardEntityService constrEntityService() {
        return new CardEntityServiceImpl();
    }

    @Bean(name = "mainDBCardEntityAttrService")
    public CardEntityAttrService constrEntityAttrService() {
        return new CardEntityAttrServiceImpl();
    }

    @Bean(name = "mainDBLanguageService")
    public LanguageService languageService() {
        return new LanguageServiceImpl();
    }

    @Bean(name = "mainDBValidatorService")
    public ValidatorService validatorService() {
        return new ValidatorServiceImpl();
    }

    @Bean(name = "mainDBModuleService")
    public ModuleService moduleService() {
        return new ModuleServiceImpl();
    }

    @Bean(name = "mainDBMsgValueService")
    public MsgValueService msgValueService(@Qualifier(value = "mainDBMsgValueRepository") MsgValueRepository repository) {
        return new MsgValueServiceMainImpl(repository);
    }

    @Bean(name = "mainDBTriggerService")
    public TriggerService triggerService() {
        return new TriggerServiceImpl();
    }

    @Bean(name = "mainDBWfStatusService")
    public WfStatusService wfStatusService() {
        return new WfStatusServiceImpl();
    }

    @Bean(name = "mainDBWfTransitionTriggerService")
    public WfTransitionTriggerService wfTransitionTriggerService() {
        return new WfTransitionTriggerServiceImpl();
    }

    @Bean(name = "mainDBWfTransitionValidatorService")
    public WfTransitionValidatorService wfTransitionValidatorService() {
        return new WfTransitionValidatorServiceImpl();
    }

    @Bean(name = "mainDBWfTransitionService")
    public WfTransitionService transitionService() {
        return new WfTransitionServiceImpl();
    }
}

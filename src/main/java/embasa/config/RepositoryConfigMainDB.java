package embasa.config;

import embasa.i18n.LanguageHolder;
import embasa.i18n.LanguageHolderImpl;
import embasa.persistence.common.repository.MsgValueRepository;
import embasa.persistence.common.service.LanguageService;
import embasa.persistence.maindb.repository.*;
import embasa.persistence.maindb.repository.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class RepositoryConfigMainDB {

    @Bean(name = "mainDBJdbcTemplate")
    @Autowired
    public JdbcTemplate jdbcOperations(@Qualifier(value = "mainDBDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "mainDBLanguageHolder")
    @Autowired
    public LanguageHolder languageHolder(@Qualifier(value = "mainDBLanguageService") LanguageService languageService) {
        return new LanguageHolderImpl(languageService);
    }

    @Bean(name = "mainDBClinicRepository")
    public ClinicRepository clinicRepository() {
        return new ClinicRepositoryImpl();
    }

    @Bean(name = "mainDBCardEntityRepository")
    public CardEntityRepository sysEntityRepository() {
        return new CardEntityRepositoryImpl();
    }

    @Bean(name = "mainDBCardEntityAttrRepository")
    public CardEntityAttrRepository constrEntityAttrRepository() {
        return new CardEntityAttrRepositoryImpl();
    }

    @Bean(name = "mainDBLanguageRepository")
    public LanguageRepository languageRepository() {
        return new LanguageRepositoryImpl();
    }

    @Bean(name = "mainDBValidatorRepository")
    public ValidatorRepository validatorRepository() {
        return new ValidatorRepositoryImpl();
    }

    @Bean(name = "mainDBModuleRepository")
    public ModuleRepository moduleRepository() {
        return new ModuleRepositoryImpl();
    }

    @Bean(name = "mainDBMsgValueRepository")
    public MsgValueRepository msgValueRepository() {
        return new MainDBMsgValueRepositoryImpl();
    }

    @Bean(name = "mainDBTriggerRepository")
    public TriggerRepository triggerRepository() {
        return new TriggerRepositoryImpl();
    }

    @Bean(name = "mainDBWfStatusRepository")
    public WfStatusRepository wfStatusRepository() {
        return new WfStatusRepositoryImpl();
    }

    @Bean(name = "mainDBWfTransitionTriggerRepository")
    public WfTransitionTriggerRepository wfTransitionTriggerRepository() {
        return new WfTransitionTriggerRepositoryImpl();
    }

    @Bean(name = "mainDBWfTransitionValidatorRepository")
    public WfTransitionValidatorRepository wfTransitionValidatorRepository() {
        return new WfTransitionValidatorRepositoryImpl();
    }

    @Bean(name = "mainDBWfTransitionRepository")
    public WfTransitionRepository wfTransitionRepository() {
        return new WfTransitionRepositoryImpl();
    }
}

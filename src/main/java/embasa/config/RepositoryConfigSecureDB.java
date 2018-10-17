package embasa.config;

import embasa.i18n.LanguageHolder;
import embasa.i18n.LanguageHolderImpl;
import embasa.persistence.common.repository.MsgValueRepository;
import embasa.persistence.common.service.LanguageService;
import embasa.persistence.securedb.repository.*;
import embasa.persistence.securedb.repository.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class RepositoryConfigSecureDB {

    @Bean(name = "secureDBJdbcTemplate")
    @Autowired
    public JdbcTemplate jdbcOperations(@Qualifier(value = "secureDBDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "secureDBLanguageHolder")
    @Autowired
    public LanguageHolder languageHolder(@Qualifier(value = "secureDBLanguageService") LanguageService languageService) {
        return new LanguageHolderImpl(languageService);
    }

    @Bean(name = "secureDBLanguageRepository")
    public LanguageRepository languageRepository() {
        return new LanguageRepositoryImpl();
    }

    @Bean(name = "secureDBMsgValueRepository")
    public MsgValueRepository msgValueRepository() {
        return new SecureDBMsgValueRepositoryImpl();
    }

    @Bean(name = "secureDBAcskRepository")
    public AcskRepository acskRepository() {
        return new AcskRepositoryImpl();
    }

    @Bean(name = "secureDBUserEcpRepository")
    public UserEcpRepository userEcpRepository() {
        return new UserEcpRepositoryImpl();
    }

    @Bean(name = "secureDBDBVersionRepository")
    public DBVersionRepository dbVersionRepository() {
        return new DBVersionRepositoryImpl();
    }

    @Bean(name = "secureDBGroupRepository")
    public GroupRepository groupRepository() {
        return new GroupRepositoryImpl();
    }

    @Bean(name = "secureDBRoleRepository")
    public RoleRepository roleRepository() {
        return new RoleRepositoryImpl();
    }

    @Bean(name = "secureDBPermissionRepository")
    public PermissionRepository permissionRepository() {
        return new PermissionRepositoryImpl();
    }

    @Bean(name = "secureDBUserRepository")
    public UserRepository userRepository() {
        return new UserRepositoryImpl();
    }
}

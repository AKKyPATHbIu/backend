package embasa.config;

import embasa.persistence.common.repository.MsgValueRepository;
import embasa.persistence.common.service.LanguageService;
import embasa.persistence.common.service.MsgValueService;
import embasa.persistence.securedb.service.*;
import embasa.persistence.securedb.service.impl.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecureDBServiceConfig {

    @Bean(name = "secureDBUserService")
    public UserService secureDBUserService() {
        return new UserServiceImpl();
    }

    @Bean(name = "secureDBRoleService")
    public RoleService secureDBRoleService() { return new RoleServiceImpl(); }

    @Bean(name = "secureDBPermissionService")
    public PermissionService secureDBPermissionService() { return new PermissionServiceImpl(); }

    @Bean(name = "secureDBGroupService")
    public GroupService secureDBGroupService() { return new GroupServiceImpl(); }

    @Bean(name = "secureDBUserEcpService")
    public UserEcpService userCredentialsService() { return new UserEcpServiceImpl(); }

    @Bean(name = "secureDBDBVersionService")
    public DBVersionService sysVersionService() { return new DBVersionServiceImpl(); }

    @Bean(name = "secureDBAcskService")
    public AcskService acskService() {
        return new AcskServiceImpl();
    }

    @Bean(name = "secureDBLanguageService")
    public LanguageService languageService() {
        return new LanguageServiceImpl();
    }

    @Bean(name = "secureDBMsgValueService")
    public MsgValueService msgValueService(@Qualifier(value = "secureDBMsgValueRepository") MsgValueRepository repository) {
        return new MsgValueServiceSecureImpl(repository);
    }

}

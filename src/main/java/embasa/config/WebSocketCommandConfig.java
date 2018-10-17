package embasa.config;

import embasa.frontinteraction.command.Command;
import embasa.frontinteraction.command.impl.*;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebSocketCommandConfig implements ApplicationContextAware {

    private static ApplicationContext appContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        appContext = applicationContext;
    }

    @Bean(name = "getCurrentUserInfo")
    public Command getCurrentUserInfo() {
        return new GetCurrentUserInfo();
    }

    @Bean(name = "changePassword")
    public Command changePassword() {
        return new ChangePassword();
    }

    @Bean(name = "getDBVersion")
    public Command getDBVersion() {
        return new GetDBVersion();
    }

    @Bean(name = "getAvailableRequestsEmbas")
    public Command getAvailableRequestsEmbas() {
        return new GetAvailableRequests(appContext, "commandHolderEmbas");
    }

    @Bean(name = "getAvailableRequestsGeneral")
    public Command getAvailableRequestsGeneral() {
        return new GetAvailableRequests(appContext, "commandHolderGeneral");
    }

    @Bean(name = "getBackendVersion")
    public Command getBackendVersion() {
        return new GetBackendVersion();
    }

    @Bean(name = "getAvailableLanguages")
    public Command getAvailableLanguages() {
        return new GetAvailableLanguages();
    }

    @Bean(name = "getAvailableAcsk")
    public Command getAvailableAcsk() {
        return new GetAvailableAcsk();
    }
}

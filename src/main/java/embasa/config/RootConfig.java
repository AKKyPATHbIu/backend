package embasa.config;

import embasa.crypto.CryptoManager;
import embasa.crypto.CryptoManagerImpl;
import embasa.crypto.EcpManager;
import embasa.crypto.EcpManagerImpl;
import embasa.frontinteraction.command.*;
import embasa.i18n.Localizer;
import embasa.i18n.LocalizerImpl;
import embasa.util.PropertiesHolder;
import embasa.util.PropertiesHolderImpl;
import embasa.versioning.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
@ComponentScan(excludeFilters={@ComponentScan.Filter(org.springframework.stereotype.Controller.class)})
public class RootConfig {

    @Bean(name = "messageSource")
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("locale/message");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    @Autowired
    public Localizer localizer(@Qualifier("messageSource") MessageSource messageResource) {
        return new LocalizerImpl(messageResource);
    }

    @Bean
    public CryptoManager cryptoManager() {
        return new CryptoManagerImpl();
    }

    @Bean
    @Scope(value = "prototype")
    public EcpManager ecpManager() {
        return new EcpManagerImpl();
    }

    @Bean(name = "commandHolderEmbas")
    public CommandHolder commandHolderEmbas() {
        return new CommandHolderEmbasImpl();
    }

    @Bean(name = "commandControlEmbas")
    public CommandControl commandControl() {
        return new CommandControlImpl(commandHolderEmbas());
    }

    @Bean(name = "commandHolderGeneral")
    public CommandHolder commandHolderGeneral() {
        return new CommandHolderGeneralImpl();
    }

    @Bean(name = "commandControlGeneral")
    public CommandControl commandControlGeneral() {
        return new CommandControlImpl(commandHolderGeneral());
    }

    @Bean
    public PropertiesHolder propertiesHolder() {
        return new PropertiesHolderImpl();
    }

    @Bean
    public VersionParser versionParser() {
        return new VersionParserImpl();
    }

    @Bean
    @Profile("PRODUCTION")
    public DBVersionChecker dbVersionCheckerProduction() {
        return new DBVersionCheckerImplProduction(versionParser(), propertiesHolder());
    }

    @Bean
    @Profile("DEV")
    public DBVersionChecker dbVersionCheckerDev() {
        return new DBVersionCheckerImplDev();
    }

    @Bean
    public FrontendVersionChecker frontendVersionChecker() {
        return new FrontendVersionCheckerImpl(versionParser(), propertiesHolder());
    }
}

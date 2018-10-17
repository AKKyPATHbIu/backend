package embasa.config;

import embasa.connection.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("DEV")
public class ConnectionConfigDev {

    @Bean
    public ConnectionPropertyLoaderFromFile hibernatePropertyLoaderTest() {
        return new ConnectionPropertyLoaderDevImpl();
    }

    @Bean
    public ConnectionPropertiesTransformer connectionPropertiesTransformer() {
        return new ConnectionPropertiesTransformerImpl();
    }

    @Bean
    public ConnectionCreator dbConnectionCreator() {
        return new ConnectionCreatorImpl(hibernatePropertyLoaderTest(), connectionPropertiesTransformer());
    }
}

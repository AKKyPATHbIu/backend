package embasa.config;

import embasa.connection.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("PRODUCTION")
public class ConnectionConfigProduction {

    @Bean
    public ConnectionPropertyLoaderFromFile hibernatePropertyLoaderProduction() {
        return new ConnectionPropertyLoaderProductionImpl();
    }

    @Bean
    public ConnectionPropertiesTransformer connectionPropertiesTransformer() {
        return new ConnectionPropertiesTransformerImpl();
    }

    @Bean
    public ConnectionCreator dbConnectionCreator() {
        return new ConnectionCreatorImpl(hibernatePropertyLoaderProduction(), connectionPropertiesTransformer());
    }
}

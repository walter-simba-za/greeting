package co.za.zwibvafhi.greeting.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.atomic.AtomicLong;

@Configuration
public class GreetingConfig {

    @Qualifier("GreetingCounter")
    @Bean
    public AtomicLong counter() {
        return new AtomicLong(0);
    }
}

package co.za.zwibvafhi.greeting.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "greeting")
@Data
public class GreetingProperties {
    private String template;
}

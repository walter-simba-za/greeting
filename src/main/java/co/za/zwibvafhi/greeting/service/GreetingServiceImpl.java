package co.za.zwibvafhi.greeting.service;

import co.za.zwibvafhi.greeting.model.Greeting;
import co.za.zwibvafhi.greeting.config.GreetingProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
@Slf4j
@RequiredArgsConstructor
public class GreetingServiceImpl implements GreetingService {

    private final GreetingProperties properties;
    @Qualifier("GreetingCounter")
    private final AtomicLong counter;
    @Override
    public Greeting greet(String name) {
        log.debug("Greet {}", name);
        final String content = String.format(properties.getTemplate(), name);
        return new Greeting(counter.incrementAndGet(), content);
    }

}

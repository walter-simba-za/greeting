package co.za.zwibvafhi.greeting.controller;

import co.za.zwibvafhi.greeting.model.Greeting;
import co.za.zwibvafhi.greeting.service.GreetingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GreetingController {

    private final GreetingService service;

    @GetMapping
    @ResponseBody
    public Greeting greet(@RequestParam(name = "name", defaultValue = "World") String name) {
        log.debug("Received name: {}", name);
        return service.greet(name);
    }

}

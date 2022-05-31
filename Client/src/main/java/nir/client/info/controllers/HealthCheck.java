package nir.client.info.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

@RestController
public class HealthCheck {

    @RequestMapping(value = "/health", consumes = MediaType.ALL_VALUE, produces = MediaType.ALL_VALUE)
    public String check() {
        return "Client:Hello world!";
    }
}
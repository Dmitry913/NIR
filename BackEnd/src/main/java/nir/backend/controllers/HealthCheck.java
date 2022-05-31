package nir.backend.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {

    @RequestMapping(value = "/health")
    public String check() {
        return "BackEnd:Hello world!";
    }
}
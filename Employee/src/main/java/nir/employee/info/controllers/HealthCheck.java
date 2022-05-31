package nir.employee.info.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {

    @RequestMapping("/health")
    public String check() {
        return "Employee:Hello world!";
    }
}
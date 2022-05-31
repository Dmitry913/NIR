package nir.bh.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HealthCheck {

    @Value("${client-info-url}")
    private String clientURL = "http://localhost:8090";
    @Value("${call-report-info-url}")
    private String callReportURL = "http://localhost:8060";
    @Value("${employee-info-url}")
    private String employeeURL = "http://localhost:8070";
    @RequestMapping("/health")
    public String check() {
        return "BH:Hello world!";
    }

    @RequestMapping(value = "/checkAllConnection", consumes = MediaType.ALL_VALUE, produces = MediaType.ALL_VALUE)
    public String healthCheck() {
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("BH check");
        restTemplate.getForObject(clientURL + "/health", String.class);
        System.out.println("client check");
        restTemplate.getForObject(callReportURL + "/health", String.class);
        System.out.println("callReport check");
        restTemplate.getForObject(employeeURL + "/health", String.class);
        System.out.println("employee check");
        return "BH:Hello world!";
    }

}
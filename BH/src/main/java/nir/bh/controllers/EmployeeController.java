package nir.bh.controllers;

import lombok.RequiredArgsConstructor;
import nir.bh.models.EmployeeModel;
import nir.bh.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/clients",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class EmployeeController {
    private final Logger logger = LoggerFactory.getLogger(CallReportController.class);

    private final EmployeeService employeeService;

    @GetMapping
    public List<EmployeeModel> getEmployees(@RequestParam String name) {
        logger.info("Get all employees with name = {}", name);
        return employeeService.getAllEmployee(name);
    }
}

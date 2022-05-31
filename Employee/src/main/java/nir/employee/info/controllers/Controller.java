package nir.employee.info.controllers;


import lombok.RequiredArgsConstructor;
import nir.employee.info.error.IdNotFoundException;
import nir.employee.info.models.EmployeeModel;
import nir.employee.info.repository.LocalEmployeeStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(value = "/employee",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class Controller {
    private final Logger logger = LoggerFactory.getLogger(Controller.class);

    private final LocalEmployeeStorage storage;

    @GetMapping("/{id}")
    public EmployeeModel getById(@PathVariable String id) {
        logger.info("Get employee by id = {}", id);
        return storage.findById(id);
    }

    @PostMapping("/info")
    public String createEmployee(@RequestBody EmployeeModel employeeModel) {
        logger.info("Create employee");
        return storage.saveEmployee(employeeModel);
    }

    @PostMapping("/info/{id}")
    public EmployeeModel updateEmployee(@RequestBody EmployeeModel updateEmployee, @PathVariable String id) {
        if (updateEmployee.getEmployeeId() != null) {
            throw new IdNotFoundException();
        }
        return storage.updateEmployee(id, updateEmployee);
    }

    @GetMapping("/{id}/fullName")
    public String getNameById(@PathVariable String id) {
        logger.info("get name client with id = {}", id);
        return storage.getFullName(id);
    }

    @GetMapping("/")
    public List<EmployeeModel> getAllEmployeeByName(@RequestParam String name) {
        logger.info("get clients with name = {}", name);
        return storage.allEmployeeByName(name);
    }
    
}
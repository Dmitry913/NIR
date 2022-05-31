package nir.employee.info.repository;


import nir.employee.info.error.IdNotFoundException;
import nir.employee.info.models.EmployeeModel;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class LocalEmployeeStorage {

    private final Map<String, EmployeeModel> employees;

    public LocalEmployeeStorage() {
        this.employees = new HashMap<>();
    }

    public String saveEmployee(EmployeeModel employeeModel) {
        if (employeeModel.getEmployeeId().isBlank()) {
            employeeModel.setEmployeeId(generateUUID());
        }
        employees.put(employeeModel.getEmployeeId(), employeeModel);
        return employeeModel.getEmployeeId();
    }

    public EmployeeModel updateEmployee(String id, EmployeeModel updateModel) {
        if (employees.containsKey(id)) {
            updateModel.setEmployeeId(id);
            employees.put(id, updateModel);
            return employees.get(id);
        } else {
            throw new IdNotFoundException();
        }
    }

    public EmployeeModel findById(String id) {
        if (employees.containsKey(id)) {
            return employees.get(id);
        } else {
            throw new IdNotFoundException();
        }
    }

    private static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public List<EmployeeModel> allEmployeeByName(String name) {
        return employees.values().stream().filter(employeeModel -> containsName(name, employeeModel)).collect(Collectors.toList());
    }

    public String getFullName(String id) {
        EmployeeModel employee = findById(id);
        return fullName(employee);
    }

    private String getNullOrEmpty(String string) {
        return string == null ? "" : string;
    }

    private String fullName(EmployeeModel clientModel) {
        return String.format("%s %s %s",
                getNullOrEmpty(clientModel.getFirstName()),
                getNullOrEmpty(clientModel.getMiddleName()),
                getNullOrEmpty(clientModel.getLastName()));
    }

    private Boolean containsName(String name, EmployeeModel employeeModel) {
        return employeeModel.getFirstName().contains(name) ||
                employeeModel.getMiddleName().contains(name) ||
                employeeModel.getLastName().contains(name) ||
                name.equals(fullName(employeeModel));
    }
}

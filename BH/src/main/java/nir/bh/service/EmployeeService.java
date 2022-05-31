package nir.bh.service;

import nir.bh.models.EmployeeModel;

import java.util.List;

public interface EmployeeService {

    List<EmployeeModel> getAllEmployee(String name);

}

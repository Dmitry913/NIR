package nir.bh.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import nir.bh.error.MappingException;
import nir.bh.models.EmployeeModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Value("${employee-info-url}")
    private String employeeURL;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public EmployeeServiceImpl() {
        this.objectMapper = new ObjectMapper();
        this.restTemplate = new RestTemplate();
    }

    @Override
    public List<EmployeeModel> getAllEmployee(String name) {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<EmployeeModel> employeeModels = getEmployees(name);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        return employeeModels;
    }

    private List<EmployeeModel> getEmployees(String name) {
        List<EmployeeModel> employeeModels;
        try {
            employeeModels = objectMapper.readValue(restTemplate.getForObject(employeeURL, String.class, name),
                    new TypeReference<>() {});
        } catch (Exception e) {
            throw new MappingException();
        }
        return employeeModels;
    }
}

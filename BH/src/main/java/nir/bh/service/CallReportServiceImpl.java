package nir.bh.service;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import nir.bh.error.MappingException;
import nir.bh.models.CallReportModel;
import nir.bh.models.ClientModel;
import nir.bh.models.EmployeeModel;
import nir.bh.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CallReportServiceImpl implements CallReportService {

    @Value("${client-info-url}")
    private String clientURL;
    @Value("${call-report-info-url}")
    private String callReportURL;
    @Value("${employee-info-url}")
    private String employeeURL;

    private ClientService clientService;
    private EmployeeService employeeService;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public CallReportServiceImpl() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public List<CallReportModel> getAllCallReport(String topic) {
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        List<CallReportModel> callReports = getCallReports(topic);
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);
        return callReports;
    }

    private List<CallReportModel> getCallReports(String topic) {
        List<CallReportModel> callReports;
        try {
            callReports = objectMapper.readValue(restTemplate.getForObject(callReportURL, String.class, topic),
                    new TypeReference<>() {});
        } catch (Exception e) {
            throw new MappingException();
        }
       findNamesClientEmployeeAssignee(callReports);
        return callReports;
    }


    @Override
    public CallReportModel getCallReport(String id) {
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        CallReportModel callReport = getCallReportById(id);
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);
        return callReport;
    }

    private CallReportModel getCallReportById(String id) {
        CallReportModel callReport;
        try {
            String response = restTemplate.getForObject(String.format("%s/%s", callReportURL, id), String.class);
            callReport = objectMapper.readValue(response, new TypeReference<>() {});
        } catch (Exception e) {
            throw new MappingException();
        }
        findNamesClientEmployeeAssignee(List.of(callReport));
        return callReport;
    }

    private void findNamesClientEmployeeAssignee(List<CallReportModel> callReportModels) {
        callReportModels.stream()
                .flatMap(callReport -> callReport.getClients().stream())
                .forEach(client -> client.setFromFullName(restTemplate.getForObject(
                        String.format("%s/%s/organization", clientURL, client.getId()), String.class)));
        callReportModels.stream()
                .flatMap(callReportModel -> {
                    List<EmployeeModel> employeeModels = callReportModel.getEmployees();
                    employeeModels.addAll(callReportModel.getAgreements().stream().map(agreementModel -> new EmployeeModel(agreementModel.getAssigner())).collect(Collectors.toList()));
                    return employeeModels.stream();
                })
                .forEach(employeeModel -> employeeModel.setFromFullName(restTemplate.getForObject(
                        String.format("%s/%s/", employeeURL, employeeModel.getId()), String.class)));
    }


    @Override
    public String createCallReport(CallReportModel callReportModel) {
        String id;
        try {
            createClients(callReportModel.getClients());
            createEmployees(callReportModel.getEmployees());
            createEmployees(callReportModel.getAgreements().stream()
                    .map(agreementModel -> new EmployeeModel(agreementModel.getAssigner())).collect(Collectors.toList()));
            id = objectMapper.readValue(restTemplate.postForObject(callReportURL, new HttpEntity<>(callReportModel), String.class),
                    new TypeReference<>() {});
        } catch (JacksonException e) {
            throw new MappingException();
        }
        return id;
    }

    private void createEmployees(List<EmployeeModel> employeeModels) throws JsonProcessingException {
        for (EmployeeModel employeeModel : employeeModels) {
            List<EmployeeModel> clientsExternal = employeeService.getAllEmployee(fullName(employeeModel));
            if (clientsExternal.isEmpty()) {
                String employeeId = objectMapper.readValue(restTemplate
                        .postForObject(employeeURL, new HttpEntity<>(employeeModel), String.class), new TypeReference<>() {});
                employeeModel.setId(employeeId);
            }
        }
    }

    private void createClients(List<ClientModel> clientModels) throws JsonProcessingException {
        for (ClientModel clientModel : clientModels) {
            List<ClientModel> clientsExternal = clientService.getAllClients(fullName(clientModel));
            if (clientsExternal.isEmpty()) {
                String clientId = objectMapper.readValue(restTemplate
                        .postForObject(clientURL, new HttpEntity<>(clientModel), String.class), new TypeReference<>() {});
                clientModel.setId(clientId);
            }
        }
    }

    private String fullName(Person person) {
        return String.format("%s %s %s",
                getNullOrEmpty(person.getFirstName()),
                getNullOrEmpty(person.getMiddleName()),
                getNullOrEmpty(person.getLastName()));
    }

    private String getNullOrEmpty(String string) {
        return string == null ? "" : string;
    }

}
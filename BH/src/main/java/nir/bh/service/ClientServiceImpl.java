package nir.bh.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import nir.bh.error.MappingException;
import nir.bh.models.ClientModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Value("${client-info-url}")
    private String clientURL;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public ClientServiceImpl() {
        this.objectMapper = new ObjectMapper();
        this.restTemplate = new RestTemplate();
    }

    @Override
    public List<ClientModel> getAllClients(String name) {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<ClientModel> clientModels = getClients(name);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        return clientModels;
    }

    private List<ClientModel> getClients(String name) {
        List<ClientModel> clientModels;
        try {
            clientModels = objectMapper.readValue(restTemplate.getForObject(clientURL, String.class, name),
                    new TypeReference<>() {});
        } catch (Exception e) {
            throw new MappingException();
        }
        return clientModels;
    }
}

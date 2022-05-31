package nir.client.info.repository;


import nir.client.info.error.IdNotFoundException;
import nir.client.info.models.ClientModel;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class LocalClientStorage {

    private final Map<String, ClientModel> clients;

    public LocalClientStorage() {
        this.clients = new HashMap<>();
    }

    public String saveClient(ClientModel clientModel) {
        if (clientModel.getClientId().isBlank()) {
            clientModel.setClientId(generateUUID());
        }
        clients.put(clientModel.getClientId(), clientModel);
        return clientModel.getClientId();
    }

    public ClientModel findById(String id) {
        if (clients.containsKey(id)) {
            return clients.get(id);
        } else {
            throw new IdNotFoundException();
        }
    }

    public String getFullName(String id) {
        ClientModel client = findById(id);
        return fullName(client);
    }

    private String fullName(ClientModel clientModel) {
        return String.format("%s %s %s",
                getNullOrEmpty(clientModel.getFirstName()),
                getNullOrEmpty(clientModel.getMiddleName()),
                getNullOrEmpty(clientModel.getLastName()));
    }

    private String getNullOrEmpty(String string) {
        return string == null ? "" : string;
    }

    private static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public List<ClientModel> allClientByName(String name) {
        return clients.values().stream().filter(clientModel -> containsName(name, clientModel)).collect(Collectors.toList());
    }

    private Boolean containsName(String name, ClientModel clientModel) {
        return clientModel.getFirstName().contains(name) ||
                clientModel.getMiddleName().contains(name) ||
                clientModel.getLastName().contains(name) ||
                name.equals(fullName(clientModel));
    }
}
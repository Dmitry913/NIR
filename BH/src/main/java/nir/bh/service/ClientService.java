package nir.bh.service;

import nir.bh.models.ClientModel;

import java.util.List;

public interface ClientService {

    List<ClientModel> getAllClients(String name);
}

package nir.client.info.controllers;


import lombok.RequiredArgsConstructor;
import nir.client.info.models.ClientModel;
import nir.client.info.repository.LocalClientStorage;
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
@RequestMapping(value = "/clients",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class Controller {
    private final Logger logger = LoggerFactory.getLogger(Controller.class);

    private final LocalClientStorage storage;

    @GetMapping("/{id}")
    public ClientModel getById(@PathVariable String id) {
        logger.info("get client by id = {}", id);
        return storage.findById(id);
    }

    @GetMapping("/{id}/fullName")
    public String getNameById(@PathVariable String id) {
        logger.info("get name client with id = {}", id);
        return storage.getFullName(id);
    }

    @PostMapping("/")
    public String createClient(@RequestBody ClientModel clientModel) {
        logger.info("create new client");
        return storage.saveClient(clientModel);
    }

    @GetMapping("/")
    public List<ClientModel> getAllClientsByName(@RequestParam String name) {
        logger.info("get clients with name = {}", name);
        return storage.allClientByName(name);
    }
}
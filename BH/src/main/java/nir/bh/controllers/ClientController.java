package nir.bh.controllers;

import lombok.RequiredArgsConstructor;
import nir.bh.models.ClientModel;
import nir.bh.service.ClientService;
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
public class ClientController {
    private final Logger logger = LoggerFactory.getLogger(CallReportController.class);

    private final ClientService clientService;

    @GetMapping("/")
    public List<ClientModel> getClients(@RequestParam String name) {
        logger.info("Get all clients with name = {}", name);
        return clientService.getAllClients(name);
    }
}

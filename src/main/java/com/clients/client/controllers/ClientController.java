package com.clients.client.controllers;

import java.util.List;
import java.util.stream.Collectors;

import com.clients.client.items.Client;
import com.clients.ClientNotFoundException;
import com.clients.client.repositories.ClientRepository;
import com.clients.ClientModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class ClientController {
    @Autowired
    private ClientRepository repository;
    private ClientModelAssembler assembler;

    @GetMapping(value = "/clients", produces = "application/json")
    public CollectionModel<EntityModel<Client>> all() {
        List<EntityModel<Client>> clients = repository.findAll().stream()
                .map(client -> assembler.toModel(client))
                .collect(Collectors.toList());

        return new CollectionModel<EntityModel<Client>>(
                clients,
                linkTo(methodOn(ClientController.class).all()).withSelfRel()
        );
    }

    @PostMapping("/clients")
    public ResponseEntity<?> newClient(@RequestBody Client newClient) {
        EntityModel<Client> entityModel = assembler.toModel(repository.save(newClient));

        return ResponseEntity
                .created(entityModel
                .getRequiredLink(IanaLinkRelations.SELF)
                .toUri())
                .body(entityModel);
    }

    @GetMapping("/clients/{id}")
    public EntityModel<Client> one(@PathVariable Long id) {
        Client client = repository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
        return assembler.toModel(client);
    }

    @PutMapping("/clients/{id}")
    public ResponseEntity<?> replaceClient(@RequestBody Client newClient, @PathVariable Long id) {
        Client updatedClient = repository.findById(id)
                .map(client -> {
                    client.setName(newClient.getName());
                    client.setAddress(newClient.getAddress());
                    client.setEmail(newClient.getEmail());
                    client.setCity(newClient.getCity());
                    client.setState(newClient.getState());
                    client.setCountry(newClient.getCountry());
                    client.setPhoneNumber(newClient.getPhoneNumber());
                    client.setCorporationType(newClient.getCorporationType());
                    return repository.save(client);
                })
                .orElseGet(() -> {
                    newClient.setId(id);
                    return repository.save(newClient);
                });
        EntityModel<Client> entityModel = assembler.toModel(updatedClient);

        return ResponseEntity
                .created(entityModel
                .getRequiredLink(IanaLinkRelations.SELF)
                .toUri())
                .body(entityModel);
    }

    @DeleteMapping("/clients/{id}")
    public void deleteClient(@PathVariable Long id) {
        repository.deleteById(id);
    }
}

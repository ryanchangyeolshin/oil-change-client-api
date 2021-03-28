package com.clients.client.controllers;

import java.util.List;
import java.util.stream.Collectors;

import com.clients.client.items.Client;
import com.clients.client.ClientNotFoundException;
import com.clients.client.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class ClientController {
    @Autowired
    private ClientRepository repository;

    @GetMapping(value = "/clients", produces = "application/json")
    CollectionModel<EntityModel<Client>> all() {
        List<EntityModel<Client>> clients = repository.findAll().stream().map(
                client -> new EntityModel<Client>(
                        client,
                        linkTo(methodOn(ClientController.class).one(client.getId())).withSelfRel(),
                        linkTo(methodOn(ClientController.class).all()).withRel("clients"))
        ).collect(Collectors.toList());

        return new CollectionModel<EntityModel<Client>>(
                clients,
                linkTo(methodOn(ClientController.class).all()).withSelfRel()
        );
    }

    @PostMapping("/clients")
    Client newClient(@RequestBody Client newClient) {
        return repository.save(newClient);
    }

    @GetMapping("/clients/{id}")
    EntityModel<Client> one(@PathVariable Long id) {
        Client client = repository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
        return new EntityModel<Client>(
                client,
                linkTo(methodOn(ClientController.class).one(id)).withSelfRel(),
                linkTo(methodOn(ClientController.class).all()).withRel("clients")
        );
    }

    @PutMapping("/clients/{id}")
    Client replaceClient(@RequestBody Client newClient, @PathVariable Long id) {
        return repository.findById(id)
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
    }

    @DeleteMapping("/clients/{id}")
    void deleteClient(@PathVariable Long id) {
        repository.deleteById(id);
    }
}

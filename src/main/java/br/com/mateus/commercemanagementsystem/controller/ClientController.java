package br.com.mateus.commercemanagementsystem.controller;

import br.com.mateus.commercemanagementsystem.model.Client;
import br.com.mateus.commercemanagementsystem.service.serviceImpl.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientServiceImpl clientService;

    @PostMapping("/clients")
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        clientService.createClient(client);
        return ResponseEntity.ok().body(client);
    }
}

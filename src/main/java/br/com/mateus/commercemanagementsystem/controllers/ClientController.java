package br.com.mateus.commercemanagementsystem.controllers;

import br.com.mateus.commercemanagementsystem.model.Client;
import br.com.mateus.commercemanagementsystem.service.serviceImpl.ClientServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientController {

    private final ClientServiceImpl clientService;

    public ClientController(ClientServiceImpl clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/clients")
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        clientService.createClient(client);
        return ResponseEntity.ok().body(client);
    }

    @PutMapping("/clients")
    public ResponseEntity<Client> updateClient(@RequestBody Client client) {
        clientService.updateClient(client);
        return ResponseEntity.ok().body(client);
    }

    @DeleteMapping("/clients/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable String id) {
        clientService.deleteByCpf(id);
        return ResponseEntity.ok().body("Cliente deletado com sucesso. \n - ID " + id);
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<Client> findByCpf(@PathVariable String id) {
        Client client = clientService.findByCpf(id);
        return ResponseEntity.ok().body(client);
    }

    @GetMapping("/clients/byName/{name}")
    public ResponseEntity<List<Client>> findByName(@PathVariable String name) {
        List<Client> list = clientService.findByName(name);
        return ResponseEntity.ok().body(list);
    }
}

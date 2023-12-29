package br.com.mateus.commercemanagementsystem.controllers;

import br.com.mateus.commercemanagementsystem.dto.CustomerCreatedDTO;
import br.com.mateus.commercemanagementsystem.dto.CustomerDTO;
import br.com.mateus.commercemanagementsystem.model.Customer;
import br.com.mateus.commercemanagementsystem.services.serviceImpl.CustomerServiceImpl;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerServiceImpl customerService;

    public CustomerController(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }

    @PostMapping()
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {
        customerService.createCustomer(customer);
        return ResponseEntity.ok().body(customer);
    }

    @PutMapping()
    public ResponseEntity<Customer> updateCustomer(@Valid @RequestBody Customer customer) {
        customerService.updateCustomer(customer);
        return ResponseEntity.ok().body(customer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable String id) {
        customerService.deleteByCpf(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> findByCpf(@PathVariable String id) {
        Customer customer = customerService.findByCpf(id);
        return ResponseEntity.ok().body(customer);
    }

    @GetMapping("/byName/{name}")
    public ResponseEntity<List<Customer>> findByName(@PathVariable String name) {
        List<Customer> list = customerService.findByName(name);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping
    public ResponseEntity<List<CustomerCreatedDTO>> listAll() {
        return ResponseEntity.ok().body(customerService.findAll());
    }
}

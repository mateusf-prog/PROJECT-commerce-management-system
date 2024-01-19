package br.com.mateus.commercemanagementsystem.controllers;

import br.com.mateus.commercemanagementsystem.dto.CustomerCreatedOrUpdatedDTO;
import br.com.mateus.commercemanagementsystem.dto.CustomerDTO;
import br.com.mateus.commercemanagementsystem.model.Customer;
import br.com.mateus.commercemanagementsystem.services.CustomerService;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping()
    public ResponseEntity<CustomerCreatedOrUpdatedDTO> createCustomer(@Valid @RequestBody Customer customer) {
        CustomerCreatedOrUpdatedDTO dto = customerService.createCustomer(customer);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping()
    public ResponseEntity<CustomerCreatedOrUpdatedDTO> updateCustomer(@Valid @RequestBody Customer customer) {
        CustomerCreatedOrUpdatedDTO dto = customerService.updateCustomer(customer);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> findByCpf(@PathVariable String id) {
        CustomerDTO entity = customerService.findByCpf(id);
        return ResponseEntity.ok().body(entity);
    }

    @GetMapping
    public ResponseEntity<List<CustomerCreatedOrUpdatedDTO>> listAll() {
        return ResponseEntity.ok().body(customerService.findAll());
    }
}

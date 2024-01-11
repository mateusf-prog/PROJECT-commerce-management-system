package br.com.mateus.commercemanagementsystem.controllers;

import br.com.mateus.commercemanagementsystem.dto.OrderCreatedDTO;
import br.com.mateus.commercemanagementsystem.dto.OrderDTO;
import br.com.mateus.commercemanagementsystem.dto.OrderPostDTO;
import br.com.mateus.commercemanagementsystem.services.services_impl.OrderServiceImpl;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderServiceImpl orderService;

    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @PostMapping()
    public ResponseEntity<OrderCreatedDTO> create(@Valid @RequestBody OrderPostDTO order) {
        return ResponseEntity.ok(orderService.createOrder(order));
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<List<OrderDTO>> findByCustomerCpf(@PathVariable String cpf) {
        List<OrderDTO> list = orderService.findByCustomerCpf(cpf);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<OrderDTO> findByCustomerCpf(@PathVariable Long id) {
        OrderDTO order = orderService.findById(id);
        return ResponseEntity.ok().body(order);
    }

    @GetMapping()
    public ResponseEntity<List<OrderDTO>> findAll() {
        List<OrderDTO> list = orderService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PatchMapping("/cancel/{id}")
    public ResponseEntity<OrderDTO> cancel(@PathVariable Long id) {
        OrderDTO orderDTO = orderService.cancelOrder(id);
        return ResponseEntity.ok().body(orderDTO);
    }
}

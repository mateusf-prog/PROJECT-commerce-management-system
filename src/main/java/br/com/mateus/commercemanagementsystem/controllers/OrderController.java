package br.com.mateus.commercemanagementsystem.controllers;

import br.com.mateus.commercemanagementsystem.dto.OrderCreatedDTO;
import br.com.mateus.commercemanagementsystem.dto.OrderDTO;
import br.com.mateus.commercemanagementsystem.services.serviceImpl.OrderServiceImpl;
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
    public ResponseEntity<OrderCreatedDTO> createOrder(@Valid @RequestBody OrderDTO order) {
        OrderCreatedDTO orderCreated = orderService.createOrder(order);
        return ResponseEntity.ok().body(orderCreated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        OrderDTO orderDTO = orderService.findById(id);
        return ResponseEntity.ok().body(orderDTO);
    }

    @GetMapping("/byCpf/{cpf}")
    public ResponseEntity<List<OrderDTO>> findAllOrdersByClientCpf(@PathVariable String cpf) {
        List<OrderDTO> list = orderService.findAllOrdersByClientCpf(cpf);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping()
    public ResponseEntity<List<OrderDTO>> findAll() {
        List<OrderDTO> list = orderService.findAll();
        return ResponseEntity.ok().body(list);
    }
}

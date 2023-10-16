package br.com.mateus.commercemanagementsystem.controllers;

import br.com.mateus.commercemanagementsystem.dto.OrderDTO;
import br.com.mateus.commercemanagementsystem.model.Order;
import br.com.mateus.commercemanagementsystem.service.serviceImpl.OrderServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class OrderController {

    private final OrderServiceImpl orderService;

    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders")
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO order) {
        orderService.createOrder(order);
        return ResponseEntity.ok().body(order);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        OrderDTO orderDTO = orderService.findById(id);
        return ResponseEntity.ok().body(orderDTO);
    }

    @GetMapping("/orders/byCpf/{cpf}")
    public ResponseEntity<List<OrderDTO>> findAllOrdersByClientCpf(@PathVariable String cpf) {
        List<OrderDTO> list = orderService.findAllOrdersByClientCpf(cpf);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> findAll() {
        List<OrderDTO> list = orderService.findAll();
        return ResponseEntity.ok().body(list);
    }
}

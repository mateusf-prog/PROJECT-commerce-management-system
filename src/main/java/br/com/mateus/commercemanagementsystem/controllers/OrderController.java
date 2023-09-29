package br.com.mateus.commercemanagementsystem.controllers;

import br.com.mateus.commercemanagementsystem.dto.OrderDTO;
import br.com.mateus.commercemanagementsystem.service.serviceImpl.OrderServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/orders")
    public ResponseEntity<OrderDTO> updateOrder(@RequestBody OrderDTO orderDTO) {
        return null;
    }

}

package br.com.mateus.commercemanagementsystem.controllers;

import br.com.mateus.commercemanagementsystem.dto.OrderCreatedDTO;
import br.com.mateus.commercemanagementsystem.dto.OrderDTO;
import br.com.mateus.commercemanagementsystem.dto.OrderPostDTO;
import br.com.mateus.commercemanagementsystem.model.Order;
import br.com.mateus.commercemanagementsystem.model.OrderItem;
import br.com.mateus.commercemanagementsystem.repository.OrderItemRepository;
import br.com.mateus.commercemanagementsystem.services.serviceImpl.OrderServiceImpl;
import jakarta.validation.Valid;

import org.antlr.v4.runtime.atn.SemanticContext;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable Long id) {
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

    @PatchMapping("/cancel/{id}")
    public ResponseEntity<OrderDTO> cancel(@PathVariable Long id) {
        OrderDTO orderDTO = orderService.cancelOrder(id);
        return ResponseEntity.ok().body(orderDTO);
    }
}

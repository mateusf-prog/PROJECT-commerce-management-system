package br.com.mateus.commercemanagementsystem.controllers;

import br.com.mateus.commercemanagementsystem.dto.PaymentReturnDTO;
import br.com.mateus.commercemanagementsystem.dto.PaymentPostDTO;
import br.com.mateus.commercemanagementsystem.model.Payment;
import br.com.mateus.commercemanagementsystem.services.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping()
    public ResponseEntity<PaymentReturnDTO> create(@Valid @RequestBody PaymentPostDTO dto) {
        PaymentReturnDTO payment = paymentService.createPayment(dto);
        return ResponseEntity.ok().body(payment);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<PaymentReturnDTO> findByOrderId(@PathVariable Long orderId) {
        PaymentReturnDTO payment = paymentService.findByOrderId(orderId);
        return ResponseEntity.ok().body(payment);
    }

}

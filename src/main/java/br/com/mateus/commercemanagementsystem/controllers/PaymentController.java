package br.com.mateus.commercemanagementsystem.controllers;

import br.com.mateus.commercemanagementsystem.model.Payment;
import br.com.mateus.commercemanagementsystem.service.serviceImpl.PaymentServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mateus.commercemanagementsystem.repository.PaymentRepository;

import java.util.List;

@RestController
@RequestMapping("api/payments")
public class PaymentController {

    private final PaymentServiceImpl paymentService;

    public PaymentController(PaymentServiceImpl paymentService) {
        this.paymentService = paymentService;
    }


    @GetMapping()
    public ResponseEntity<List<Payment>> findAll() {
        return null;
        // todo: implementar
    }
}

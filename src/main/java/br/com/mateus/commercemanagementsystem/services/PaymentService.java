package br.com.mateus.commercemanagementsystem.services;

import br.com.mateus.commercemanagementsystem.model.Payment;
import br.com.mateus.commercemanagementsystem.model.enums.PaymentStatus;

public interface PaymentService {

    Payment createPayment(String customerCpf);
    Payment findById(Long id);

    String setStatus(Payment payment, PaymentStatus status);
    String sendToEmail(Payment payment, String email);
}

package br.com.mateus.commercemanagementsystem.services;

import br.com.mateus.commercemanagementsystem.model.Order;
import br.com.mateus.commercemanagementsystem.model.Payment;
import br.com.mateus.commercemanagementsystem.model.enums.PaymentStatus;
import br.com.mateus.commercemanagementsystem.model.enums.PaymentType;

public interface PaymentService {

    Payment createPayment(Order order, PaymentType type);
    Payment findById(Long id);

    String setStatus(Payment payment, PaymentStatus status);
    String sendToEmail(Payment payment, String email);
}
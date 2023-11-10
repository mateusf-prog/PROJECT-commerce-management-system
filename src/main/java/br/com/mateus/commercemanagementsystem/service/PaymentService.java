package br.com.mateus.commercemanagementsystem.service;

import br.com.mateus.commercemanagementsystem.model.Order;
import br.com.mateus.commercemanagementsystem.model.Payment;
import br.com.mateus.commercemanagementsystem.model.enums.PaymentStatus;
import br.com.mateus.commercemanagementsystem.model.enums.PaymentType;

public interface PaymentService {

    Payment createPayment(Order order);
    Payment updatePayment(Payment payment);
    Payment findById(Long id);
    String deleteById(Long id);

    String changePaymentType(Payment payment, PaymentType type);
    String changePaymentStatus(Payment payment, PaymentStatus status);
    String sendToEmail(Payment payment, String email);
}

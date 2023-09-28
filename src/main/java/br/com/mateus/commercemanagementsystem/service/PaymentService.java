package br.com.mateus.commercemanagementsystem.service;

import br.com.mateus.commercemanagementsystem.model.Payment;
import br.com.mateus.commercemanagementsystem.model.enums.PaymentStatus;
import br.com.mateus.commercemanagementsystem.model.enums.PaymentType;

public interface PaymentService {

    Payment createPayment(Payment payment);
    Payment updatePayment(Payment payment);
    Payment readPayment(Payment payment);
    String deleteById(Long id);

    String changePaymentType(Payment payment, PaymentType type);
    String changePaymentStatus(Payment payment, PaymentStatus status);
    String sendToEmail(Payment payment, String email);

    Payment processPayment(Payment payment);
    PaymentStatus callPaymentApi(Payment payment);

    boolean validateExistenceOrderInPayment(Payment payment);
}

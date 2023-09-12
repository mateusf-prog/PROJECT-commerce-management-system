package br.com.mateus.commercemanagementsystem.service;

import br.com.mateus.commercemanagementsystem.model.Client;
import br.com.mateus.commercemanagementsystem.model.Payment;
import br.com.mateus.commercemanagementsystem.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public void setNullEntityPaymentsBeforeRemoveClient(Client client) {

        List<Payment> payments = paymentRepository.findPaymentsByClient(client);

        for (Payment payment : payments) {
            payment.setClient(null);
        }
    }
}

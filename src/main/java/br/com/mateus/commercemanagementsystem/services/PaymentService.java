package br.com.mateus.commercemanagementsystem.services;

import java.time.Instant;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.mateus.commercemanagementsystem.model.Order;
import br.com.mateus.commercemanagementsystem.model.Payment;
import br.com.mateus.commercemanagementsystem.model.enums.PaymentStatus;
import br.com.mateus.commercemanagementsystem.model.enums.PaymentType;
import br.com.mateus.commercemanagementsystem.repository.PaymentRepository;
import br.com.mateus.commercemanagementsystem.services.asaas_integration.PaymentApiService;

@Service
public class PaymentService {

     private PaymentRepository repository;
     private final PaymentApiService paymentApiService;
     
     public PaymentService(PaymentRepository repository, PaymentApiService paymentApiService) {
          this.repository = repository;
          this.paymentApiService = paymentApiService;
     }

     @Transactional
     public Payment createPayment(Order order, PaymentType type) {
          
          Payment payment = new Payment();
          payment.setMoment(Instant.now());
          payment.setStatus(PaymentStatus.PENDING);
          payment.setOrder(order);
          payment.setPaymentType(type);

          // call API Asaas
          paymentApiService.createPayment(payment);

          return null;
     }

     public Payment findById(Long id) {
          // TODO Auto-generated method stub
          throw new UnsupportedOperationException("Unimplemented method 'findById'");
     }

     public String setStatus(Payment payment, PaymentStatus status) {
          // TODO Auto-generated method stub
          throw new UnsupportedOperationException("Unimplemented method 'setStatus'");
     }

     public String sendToEmail(Payment payment, String email) {
          // TODO Auto-generated method stub
          throw new UnsupportedOperationException("Unimplemented method 'sendToEmail'");
     }
     
}

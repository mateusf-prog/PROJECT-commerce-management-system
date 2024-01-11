package br.com.mateus.commercemanagementsystem.services.services_impl;

import br.com.mateus.commercemanagementsystem.model.Order;
import br.com.mateus.commercemanagementsystem.model.Payment;
import br.com.mateus.commercemanagementsystem.model.enums.PaymentStatus;
import br.com.mateus.commercemanagementsystem.repository.PaymentRepository;
import br.com.mateus.commercemanagementsystem.services.PaymentService;

public class PaymentServiceImpl implements PaymentService {

     private final PaymentRepository repository;
     private final OrderServiceImpl orderService;

     public PaymentServiceImpl(PaymentRepository repository, OrderServiceImpl orderService) {
          this.repository = repository;
          this.orderService = orderService;
     }

     @Override
     public Payment createPayment(String customerCpf) {
          return null;
          
     }

     @Override
     public Payment findById(Long id) {
          // TODO Auto-generated method stub
          throw new UnsupportedOperationException("Unimplemented method 'findById'");
     }

     @Override
     public String setStatus(Payment payment, PaymentStatus status) {
          // TODO Auto-generated method stub
          throw new UnsupportedOperationException("Unimplemented method 'setStatus'");
     }

     @Override
     public String sendToEmail(Payment payment, String email) {
          // TODO Auto-generated method stub
          throw new UnsupportedOperationException("Unimplemented method 'sendToEmail'");
     }
     
}

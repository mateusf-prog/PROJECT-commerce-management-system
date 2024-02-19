package br.com.mateus.commercemanagementsystem.services;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import br.com.mateus.commercemanagementsystem.dto.PaymentReturnDTO;
import br.com.mateus.commercemanagementsystem.dto.PaymentPostDTO;
import br.com.mateus.commercemanagementsystem.exceptions.EntityAlreadyExistsException;
import br.com.mateus.commercemanagementsystem.exceptions.EntityInvalidDataException;
import br.com.mateus.commercemanagementsystem.exceptions.ResourceNotFoundException;
import br.com.mateus.commercemanagementsystem.model.enums.OrderStatus;
import br.com.mateus.commercemanagementsystem.model.model_asaas_integration.BillingResponse;
import br.com.mateus.commercemanagementsystem.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.mateus.commercemanagementsystem.model.Order;
import br.com.mateus.commercemanagementsystem.model.Payment;
import br.com.mateus.commercemanagementsystem.repository.PaymentRepository;
import br.com.mateus.commercemanagementsystem.services.asaas_integration.PaymentApiService;

@Service
public class PaymentService {

     private final PaymentRepository repository;
     private final OrderRepository orderRepository;
     private final PaymentApiService paymentApiService;
     private final CustomerService customerService;
     
     public PaymentService(PaymentRepository repository, OrderRepository orderRepository, PaymentApiService paymentApiService, CustomerService customerService) {
          this.repository = repository;
          this.orderRepository = orderRepository;
          this.paymentApiService = paymentApiService;
         this.customerService = customerService;
     }

     @Transactional
     public PaymentReturnDTO create(PaymentPostDTO dto) {

          Order order = orderRepository.findById(dto.getOrderId()).orElseThrow(
                  () -> new ResourceNotFoundException("Pedido " + dto.getOrderId() + " não encontrado."));

          if(order.getPayment() != null) {
               throw new EntityAlreadyExistsException("O pedido já possui um pagamento.");
          }
          if (order.getStatus().equals(OrderStatus.CANCELLED)) {
               throw new EntityInvalidDataException("Não foi possível criar um pagamento para o pedido, pois o pedido se encontra cancelado");
          }
          
          Payment payment = new Payment();
          payment.setMoment(Instant.now());
          payment.setOrder(order);
          payment.setPaymentType(dto.getPaymentType());
          payment.setValue(order.getTotalValue());

          order.setPayment(payment);

          // call API Asaas
          BillingResponse responseApi = paymentApiService.createPayment(payment);

          payment.setStatus(responseApi.getStatus());
          payment.setIdApiExternal(responseApi.getId());
          payment.setDescription("Pedido Nº " + order.getId());
          payment.setLinkPagamento(responseApi.getBankSlipUrl());

          payment = repository.save(payment);
          return new PaymentReturnDTO(payment);
     }

     public PaymentReturnDTO findById(Long id) {

          Payment payment = repository.findById(id).orElseThrow(
                  () -> new ResourceNotFoundException("Pagamento com ID '" + id + "' não encontrado!"));

          BillingResponse responseExternalApi = paymentApiService.findById(payment.getIdApiExternal());
          payment.setStatus(responseExternalApi.getStatus());

          if (responseExternalApi.getStatus().equals("RECEIVED")) {
               payment.getOrder().setStatus(OrderStatus.PAID);
          }

          repository.save(payment);
          orderRepository.save(payment.getOrder());

          return new PaymentReturnDTO(payment);
     }

     public List<PaymentReturnDTO> findAllPaymentsByCpf(String cpf) {

          customerService.findByCpf(cpf);

          List<Order> orders = orderRepository.findByCustomerCpf(cpf);
          List<PaymentReturnDTO> listPayments = new ArrayList<>();
          try {
               orders.stream().map(x -> listPayments.add(new PaymentReturnDTO(x.getPayment()))).toList();
               return listPayments;
          } catch (NullPointerException e) {
               throw new ResourceNotFoundException("Alguns pedidos não possuem pagamentos");
          }
     }

     public PaymentReturnDTO cancel(Long id) {

          Payment payment = repository.findById(id).orElseThrow(
                  () -> new ResourceNotFoundException("Pagamento com ID " + id + " não encontrado!"));

          BillingResponse responseExternalApi = paymentApiService.cancel(payment.getIdApiExternal());

          payment.setStatus(responseExternalApi.getStatus());
          repository.save(payment);

          return new PaymentReturnDTO(payment);
     }

     // todo verificar como é o envio do email dos pagamentos
     
}

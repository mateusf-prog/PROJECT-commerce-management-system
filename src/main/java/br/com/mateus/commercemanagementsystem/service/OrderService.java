package br.com.mateus.commercemanagementsystem.service;

import br.com.mateus.commercemanagementsystem.model.Order;
import br.com.mateus.commercemanagementsystem.model.OrderItem;
import br.com.mateus.commercemanagementsystem.model.Payment;
import br.com.mateus.commercemanagementsystem.model.enums.PaymentType;

import java.util.Optional;

public interface OrderService {

    Order createOrder(Order order);
    Order readOrder(Order order);
    Order updateOrder(Order order);
    String deleteById(Long id);
    Order findById(Long id);

    void addItem(OrderItem item);
    void removeItem(OrderItem item);
    void setPayment(Payment payment);
    String changePaymentType(PaymentType paymentType);

    Optional<Exception> checkValidations(Order order);
}

package br.com.mateus.commercemanagementsystem.service;

import br.com.mateus.commercemanagementsystem.model.Client;
import br.com.mateus.commercemanagementsystem.model.Order;
import br.com.mateus.commercemanagementsystem.model.OrderItem;
import br.com.mateus.commercemanagementsystem.model.Payment;
import br.com.mateus.commercemanagementsystem.model.enums.PaymentType;

import java.util.Optional;

public interface OrderService {

    Order createOrder(Order order);
    Order updateOrder(Order order);
    String deleteById(Long id);
    Optional<Order> findById(Long id);

    void addItem(Order order, OrderItem item);

    void removeItem(OrderItem item);
    void setPayment(Payment payment);
    String changePaymentType(Order order,PaymentType paymentType);

    void checkValidations(Order order);
}

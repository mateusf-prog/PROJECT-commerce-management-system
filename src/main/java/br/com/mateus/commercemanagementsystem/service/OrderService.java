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

    String addItem(Order order, OrderItem item);
    String removeItem(Order order, OrderItem item);

    String setPayment(Order order, Payment payment);

    void checkValidations(Order order);
}

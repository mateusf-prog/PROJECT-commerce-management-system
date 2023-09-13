package br.com.mateus.commercemanagementsystem.service;

import br.com.mateus.commercemanagementsystem.model.Order;
import br.com.mateus.commercemanagementsystem.model.OrderItem;
import br.com.mateus.commercemanagementsystem.model.Payment;

public interface OrderService {

    Order createOrder(Order order);
    Order readOrder(Order order);
    Order updateOrder(Order order);
    Order deleteOrder(Order order);
    Order findById(Long id);

    void addItem(OrderItem item);
    void removeItem(OrderItem item);
    void setPayment(Payment payment);
    void changePayment(Payment payment);
}

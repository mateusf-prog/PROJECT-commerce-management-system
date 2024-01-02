package br.com.mateus.commercemanagementsystem.services;

import br.com.mateus.commercemanagementsystem.model.OrderItem;

import java.util.List;

public interface OrderItemService {

    void createOrderItem(OrderItem item);
    OrderItem updateOrderItem(OrderItem item);
    List<OrderItem> findAll();

    void validateQuantity(OrderItem item);
}

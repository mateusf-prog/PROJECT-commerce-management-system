package br.com.mateus.commercemanagementsystem.service;

import br.com.mateus.commercemanagementsystem.model.OrderItem;

import java.util.List;

public interface OrderItemService {

    void createOrderItem(OrderItem item);
    OrderItem updateOrderItem(OrderItem item);
    List<OrderItem> findAll();
    void deleteByItemName(String name);

    void validateNameAndQuantityOrderItem(OrderItem item);
}

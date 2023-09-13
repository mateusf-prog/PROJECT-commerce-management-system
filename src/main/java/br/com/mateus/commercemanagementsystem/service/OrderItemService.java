package br.com.mateus.commercemanagementsystem.service;

import br.com.mateus.commercemanagementsystem.model.OrderItem;

import java.math.BigDecimal;

public interface OrderItemService {

    OrderItem createOrderItem(OrderItem item);
    OrderItem readOrderItem(OrderItem item);
    OrderItem updateOrderItem(OrderItem item);
    OrderItem deleteOrderItem(OrderItem item);

    void setQuantity(OrderItem item, int quantity);
    void setPrice(OrderItem item, BigDecimal price);

    BigDecimal calculateTotalPrice(OrderItem item);
    BigDecimal getTotalPrice(OrderItem item);

    boolean isInStock(OrderItem item);
}

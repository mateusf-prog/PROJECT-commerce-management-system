package br.com.mateus.commercemanagementsystem.service;

import br.com.mateus.commercemanagementsystem.model.OrderItem;
import br.com.mateus.commercemanagementsystem.service.serviceImpl.ProductServiceImpl;

import java.math.BigDecimal;
import java.util.List;

public interface OrderItemService {

    OrderItem createOrderItem(OrderItem item);
    OrderItem readOrderItem(OrderItem item);
    OrderItem updateOrderItem(OrderItem item);
    List<OrderItem> findAll();
    String deleteById(Long id);

    void setQuantity(OrderItem item, int quantity);
    void setPrice(OrderItem item, BigDecimal price);

    BigDecimal calculateTotalPrice(OrderItem item);

    int verifyQuantityStockAvailability(OrderItem item);

    boolean validateQuantityItem(OrderItem item);
    boolean validatePrice(OrderItem item);
    boolean validateName(OrderItem item);
}

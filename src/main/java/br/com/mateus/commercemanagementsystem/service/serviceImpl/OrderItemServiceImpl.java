package br.com.mateus.commercemanagementsystem.service.serviceImpl;

import br.com.mateus.commercemanagementsystem.model.OrderItem;
import br.com.mateus.commercemanagementsystem.repository.OrderItemRepository;
import br.com.mateus.commercemanagementsystem.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public OrderItem createOrderItem(OrderItem item) {
        return null;
    }

    @Override
    public OrderItem readOrderItem(OrderItem item) {
        return null;
    }

    @Override
    public OrderItem updateOrderItem(OrderItem item) {
        return null;
    }

    @Override
    public List<OrderItem> findAll() {
        return null;
    }

    @Override
    public String deleteById(Long id) {
        return null;
    }

    @Override
    public void setQuantity(OrderItem item, int quantity) {

    }

    @Override
    public void setPrice(OrderItem item, BigDecimal price) {

    }

    @Override
    public BigDecimal calculateTotalPrice(OrderItem item) {
        return null;
    }

    @Override
    public BigDecimal getTotalPrice(OrderItem item) {
        return null;
    }

    // validations

    @Override
    public boolean checkStockAvailability(OrderItem item) {
        return false;
    }

    @Override
    public boolean validateQuantityItem(OrderItem item) {
        return false;
    }

    @Override
    public boolean validatePrice(OrderItem item) {
        return false;
    }
}

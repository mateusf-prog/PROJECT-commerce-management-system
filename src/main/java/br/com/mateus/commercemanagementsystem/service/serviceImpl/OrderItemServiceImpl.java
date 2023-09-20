package br.com.mateus.commercemanagementsystem.service.serviceImpl;

import br.com.mateus.commercemanagementsystem.exceptions.orderItem.InvalidPriceOrderItemException;
import br.com.mateus.commercemanagementsystem.exceptions.orderItem.InvalidQuantityOrderItemException;
import br.com.mateus.commercemanagementsystem.model.OrderItem;
import br.com.mateus.commercemanagementsystem.repository.OrderItemRepository;
import br.com.mateus.commercemanagementsystem.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductServiceImpl productService;

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

        return orderItemRepository.findAll();
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
        return item.getPrice().multiply(new BigDecimal(item.getQuantity()));
    }

    @Override
    public int verifyQuantityStockAvailability(OrderItem item) {

        return productService.checkQuantityStockAvailability(item.getProductName());
    }

    // validations

    @Override
    public boolean validateQuantityItem(OrderItem item) {

        if(item.getQuantity() <= 0) {
            throw new InvalidQuantityOrderItemException("Quantidade inválida!");
        } else {
            return false;
        }
    }

    @Override
    public boolean validatePrice(OrderItem item) {

        if(item.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidPriceOrderItemException("Preço inválido!");
        } else {
            return false;
        }
    }
}

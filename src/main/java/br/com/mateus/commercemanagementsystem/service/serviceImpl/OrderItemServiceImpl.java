package br.com.mateus.commercemanagementsystem.service.serviceImpl;

import br.com.mateus.commercemanagementsystem.exceptions.EntityInvalidDataException;
import br.com.mateus.commercemanagementsystem.exceptions.orderItem.InvalidOrderItemNameException;
import br.com.mateus.commercemanagementsystem.exceptions.orderItem.InvalidPriceOrderItemException;
import br.com.mateus.commercemanagementsystem.exceptions.orderItem.InvalidQuantityOrderItemException;
import br.com.mateus.commercemanagementsystem.exceptions.orderItem.OrderItemNotFoundException;
import br.com.mateus.commercemanagementsystem.model.OrderItem;
import br.com.mateus.commercemanagementsystem.model.Product;
import br.com.mateus.commercemanagementsystem.repository.OrderItemRepository;
import br.com.mateus.commercemanagementsystem.service.OrderItemService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductServiceImpl productService;

    @Override
    @Transactional
    public void createOrderItem(OrderItem item) {

        checkAllValidates(item);
        productService.checkQuantityStockAvailability(item.getProductName());
        orderItemRepository.save(item);

        // subtracting item from stock on Products in database
        Optional<Product> product = productService.findByName(item.getProductName());
        productService.adjustStockQuantity(product.get().getId(), product.get().getQuantity() - item.getQuantity());
    }

    @Override
    public OrderItem readOrderItem(OrderItem item) {
        return null;
    }

    @Override
    @Transactional
    public OrderItem updateOrderItem(OrderItem item) {

        Optional<OrderItem> orderItem = orderItemRepository.findById(item.getId());

        if (orderItem.isEmpty()) {
            throw new OrderItemNotFoundException("Item do pedido não encontrado - ID " + item.getId());
        }

        checkAllValidates(item);
        productService.adjustStockQuantity(item.getId(), item.getQuantity());
        orderItemRepository.save(item);

        return orderItem.get();
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
        return item.getPriceUnit().multiply(new BigDecimal(item.getQuantity()));
    }

    @Override
    public void checkAllValidates(OrderItem item) {

        if (item.getQuantity() <= 0) {
            throw new EntityInvalidDataException("Quantidade inválida!");
        }

        if (item.getPriceUnit().compareTo(BigDecimal.ZERO) <= 0) {
            throw new EntityInvalidDataException("Preço inválido!");
        }

        if (item.getProductName().isBlank() || item.getProductName().length() < 3) {
            throw new EntityInvalidDataException("Nome inválido!");
        }
    }
}

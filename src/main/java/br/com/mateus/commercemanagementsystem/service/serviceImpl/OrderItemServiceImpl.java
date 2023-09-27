package br.com.mateus.commercemanagementsystem.service.serviceImpl;

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
    public String createOrderItem(OrderItem item) {

        try {
            checkAllValidates(item);
            verifyQuantityStockAvailability(item);
            orderItemRepository.save(item);
            Optional<Product> product = productService.findByName(item.getProductName());
            productService.adjustStockQuantity(product.get().getId(), product.get().getQuantity() - item.getQuantity());
        } catch (Exception e) {
            throw e;
        }
        return "Produto adicionado!";
    }

    @Override
    public OrderItem readOrderItem(OrderItem item) {
        return null;
    }

    @Override
    public OrderItem updateOrderItem(OrderItem item) {

        Optional<OrderItem> orderItem = orderItemRepository.findById(item.getId());

        if (orderItem.isEmpty()) {
            throw new OrderItemNotFoundException("Item do pedido não encontrado!");
        }

        try {
            checkAllValidates(item);
            verifyQuantityStockAvailability(item);
            orderItemRepository.save(item);
        } catch (Exception e) {
            throw e;
        }

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
        return item.getPrice().multiply(new BigDecimal(item.getQuantity()));
    }

    @Override
    public int verifyQuantityStockAvailability(OrderItem item) {

        return productService.checkQuantityStockAvailability(item.getProductName());
    }

    @Override
    public boolean checkAllValidates(OrderItem item) {
        return validateName(item) ||
                validatePrice(item) ||
                validateQuantityInStock(item);
    }

    // validations

    @Override
    public boolean validateQuantityInStock(OrderItem item) {

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

    @Override
    public boolean validateName(OrderItem item) {

        if(item.getProductName().isBlank() || item.getProductName().length() < 3) {
            throw new InvalidOrderItemNameException("Nome inválido!");
        } else {
            return false;
        }
    }
}

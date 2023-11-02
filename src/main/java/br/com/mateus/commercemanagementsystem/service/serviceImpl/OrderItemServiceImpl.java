package br.com.mateus.commercemanagementsystem.service.serviceImpl;

import br.com.mateus.commercemanagementsystem.exceptions.EntityInvalidDataException;
import br.com.mateus.commercemanagementsystem.exceptions.EntityNotFoundException;
import br.com.mateus.commercemanagementsystem.model.OrderItem;
import br.com.mateus.commercemanagementsystem.model.Product;
import br.com.mateus.commercemanagementsystem.repository.OrderItemRepository;
import br.com.mateus.commercemanagementsystem.service.OrderItemService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final ProductServiceImpl productService;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, ProductServiceImpl productService) {
        this.orderItemRepository = orderItemRepository;
        this.productService = productService;
    }


    @Override
    @Transactional
    public void createOrderItem(@Valid OrderItem item) {

        checkAllValidates(item);
        productService.checkQuantityStockAvailability(item.getProductName());
        orderItemRepository.save(item);

        // subtracting item from stock on Products in database
        Optional<Product> product = productService.findByName(item.getProductName());
        productService.adjustStockQuantity(product.get().getName(), product.get().getQuantity() - item.getQuantity());
    }

    @Override
    @Transactional
    public OrderItem updateOrderItem(OrderItem item) {

        Optional<OrderItem> orderItem = orderItemRepository.findByProductName(item.getProductName());

        if (orderItem.isEmpty()) {
            throw new EntityNotFoundException("Item do pedido não encontrado - ID "
                    + item.getId() + " - " + item.getProductName());
        }

        checkAllValidates(item);
        productService.adjustStockQuantity(item.getProductName(), item.getQuantity());
        orderItemRepository.save(item);

        return orderItem.get();
    }

    @Override
    public List<OrderItem> findAll() {

        return orderItemRepository.findAll();
    }

    @Override
    public void deleteByItemName(String name) {

        Optional<OrderItem> orderItem = orderItemRepository.findByProductName(name);

        if(orderItem.isEmpty()) {
            throw new EntityNotFoundException("Item de pedido não encontrado. Nome: " + name);
        }

        orderItemRepository.deleteById(orderItem.get().getId());
    }

    @Override
    public void checkAllValidates(OrderItem item) {

        if (item.getQuantity() <= 0) {
            throw new EntityInvalidDataException("Quantidade de itens inválida!");
        }

        if (item.getProductName().isBlank() || item.getProductName().length() < 3) {
            throw new EntityInvalidDataException("Nome do item inválido!");
        }
    }
}

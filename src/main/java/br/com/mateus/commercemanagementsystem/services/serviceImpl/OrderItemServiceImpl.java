package br.com.mateus.commercemanagementsystem.services.serviceImpl;

import br.com.mateus.commercemanagementsystem.exceptions.EntityInvalidDataException;
import br.com.mateus.commercemanagementsystem.exceptions.ResourceNotFoundException;
import br.com.mateus.commercemanagementsystem.model.OrderItem;
import br.com.mateus.commercemanagementsystem.model.Product;
import br.com.mateus.commercemanagementsystem.repository.OrderItemRepository;
import br.com.mateus.commercemanagementsystem.services.OrderItemService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void createOrderItem(OrderItem item) {

        validateQuantity(item);
        int quantity = productService.checkQuantityStockAvailability(item.getProduct().getId());
        if (quantity < item.getQuantity()) {
            throw new EntityInvalidDataException("Quantidade indisponível no estoque!");
        }

        orderItemRepository.save(item);
        subtractingItemFromStockOnProductsDatabase(item);
    }

    @Override
    @Transactional
    public OrderItem updateOrderItem(OrderItem item) {

        Optional<OrderItem> orderItem = orderItemRepository.findById(item.getId());

        if (orderItem.isEmpty()) {
            throw new ResourceNotFoundException("Item do pedido não encontrado - ID "
                    + item.getId() + " - " + item.getProduct().getName());
        }

        validateQuantity(item);
        productService.adjustStockQuantity(item.getProduct().getId(), item.getQuantity());
        orderItemRepository.save(item);

        return orderItem.get();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderItem> findAll() {

        List<OrderItem> list = orderItemRepository.findAll();

        if (list.isEmpty()) {
            throw new ResourceNotFoundException("Lista vazia");
        }
        return list;
    }

    private void subtractingItemFromStockOnProductsDatabase(OrderItem item) {
        Product product = productService.checkProductExistsById(item.getProduct().getId());
        productService.adjustStockQuantity(product.getId(), product.getQuantity() - item.getQuantity());
    }

    @Override
    public void validateQuantity(OrderItem item) {
        if (item.getQuantity() <= 0) {
            throw new EntityInvalidDataException("Quantidade de itens inválida!");
        }
    }
}

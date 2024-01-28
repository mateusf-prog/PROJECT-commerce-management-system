package br.com.mateus.commercemanagementsystem.services;

import br.com.mateus.commercemanagementsystem.dto.OrderItemDTO;
import br.com.mateus.commercemanagementsystem.dto.OrderPostDTO;
import br.com.mateus.commercemanagementsystem.exceptions.EntityInvalidDataException;
import br.com.mateus.commercemanagementsystem.exceptions.ResourceNotFoundException;
import br.com.mateus.commercemanagementsystem.model.Order;
import br.com.mateus.commercemanagementsystem.model.OrderItem;
import br.com.mateus.commercemanagementsystem.model.Product;
import br.com.mateus.commercemanagementsystem.repository.OrderItemRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final ProductService productService;

    public OrderItemService(OrderItemRepository orderItemRepository, ProductService productService) {
        this.orderItemRepository = orderItemRepository;
        this.productService = productService;
    }

    @Transactional
    public void createOrderItem(Order order) {
        List<OrderItem> newItems = new ArrayList<>();

        for (OrderItem item : order.getItems()) {
            validateQuantity(item);
            Product product = productService.checkProductExistsById(item.getProduct().getId());
            int quantity = productService.checkQuantityStockAvailability(item.getProduct().getId());
            if (quantity < item.getQuantity()) {
                throw new EntityInvalidDataException("Quantidade indisponível no estoque. Produto: " + item.getProduct().getName());
            }
            subtractingItemFromStockOnProductsDatabase(item);
            OrderItem newItem = new OrderItem(order, product, item.getQuantity());
            newItems.add(newItem);
        }
        orderItemRepository.saveAll(newItems);
    }

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

    private void validateQuantity(OrderItem item) {
        if (item.getQuantity() <= 0) {
            throw new EntityInvalidDataException("Quantidade de itens inválida!");
        }
    }

    public List<OrderItem> convertOrderItemDTOtoOrderItemList(List<OrderItemDTO> dto) {
        List<OrderItem> newListItems = new ArrayList<>();

        for (OrderItemDTO item : dto) {
            Product product = productService.checkProductExistsById(item.getProductId());
            OrderItem newOrderItem = new OrderItem(null, product, item.getQuantity());
            newListItems.add(newOrderItem);
        }
        return newListItems;
    }
}

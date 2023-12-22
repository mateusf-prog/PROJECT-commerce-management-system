package br.com.mateus.commercemanagementsystem.services.serviceImpl;

import br.com.mateus.commercemanagementsystem.exceptions.EntityInvalidDataException;
import br.com.mateus.commercemanagementsystem.exceptions.EntityNotFoundException;
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

        validateNameAndQuantityOrderItem(item);

        int quantity = productService.checkQuantityStockAvailability(item.getProduct().getName());
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
            throw new EntityNotFoundException("Item do pedido não encontrado - ID "
                    + item.getId() + " - " + item.getProduct().getName());
        }

        validateNameAndQuantityOrderItem(item);
        productService.adjustStockQuantity(item.getProduct().getName(), item.getQuantity());
        orderItemRepository.save(item);

        return orderItem.get();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderItem> findAll() {

        return orderItemRepository.findAll();
    }


    private void subtractingItemFromStockOnProductsDatabase(OrderItem item) {
        Product product = productService.checkProductExistsByName(item.getProduct().getName());
        productService.adjustStockQuantity(product.getName(), product.getQuantity() - item.getQuantity());
    }

    @Override
    public void validateNameAndQuantityOrderItem(OrderItem item) {

        if (item.getQuantity() <= 0) {
            throw new EntityInvalidDataException("Quantidade de itens inválida!");
        }

        if (item.getProduct().getName().isBlank() || item.getProduct().getName().length() < 3) {
            throw new EntityInvalidDataException("Nome do item inválido!");
        }
    }
}

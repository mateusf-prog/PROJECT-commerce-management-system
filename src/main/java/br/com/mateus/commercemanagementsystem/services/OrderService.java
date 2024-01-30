package br.com.mateus.commercemanagementsystem.services;

import br.com.mateus.commercemanagementsystem.dto.OrderCreatedDTO;
import br.com.mateus.commercemanagementsystem.dto.OrderDTO;
import br.com.mateus.commercemanagementsystem.dto.OrderItemDTO;
import br.com.mateus.commercemanagementsystem.dto.OrderPostDTO;
import br.com.mateus.commercemanagementsystem.exceptions.EntityInvalidDataException;
import br.com.mateus.commercemanagementsystem.exceptions.ResourceNotFoundException;
import br.com.mateus.commercemanagementsystem.model.*;
import br.com.mateus.commercemanagementsystem.model.enums.OrderStatus;
import br.com.mateus.commercemanagementsystem.repository.CustomerRepository;
import br.com.mateus.commercemanagementsystem.repository.OrderRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final OrderItemService orderItemService;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository,
                            OrderItemService orderItemService, ProductService productService) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.orderItemService = orderItemService;
        this.productService = productService;
    }

    @Transactional
    public OrderCreatedDTO createOrder(OrderPostDTO dto) {

        customerRepository.findByCpf(dto.getCustomerCpf());
        Order order = convertOrderPostDTOtoOrder(dto);

        orderItemService.createOrderItem(order);
        orderRepository.save(order);

        return new OrderCreatedDTO(order, dto.getItems());
    }

    @Transactional(readOnly = true)
    public List<OrderDTO> findByCustomerCpf(String cpf) {

        List<Order> orders = orderRepository.findByCustomerCpf(cpf);
        if (orders.isEmpty()) {
            throw new ResourceNotFoundException("Cliente não possui nenhum pedido. CPF: " + cpf);
        }

        List<OrderDTO> listOrdersDTO = new ArrayList<>();
        for (Order order : orders) {
            listOrdersDTO.add(new OrderDTO(order));
        }

        return listOrdersDTO;
    }

    @Transactional(readOnly = true)
    public List<OrderDTO> findAll() {

        List<Order> list = orderRepository.findAll();
        if (list.isEmpty()) {
            throw new ResourceNotFoundException("Lista vazia!");
        }

        List<OrderDTO> listDTO = new ArrayList<>();
        for (Order order : list) {
            OrderDTO dto = new OrderDTO(order);
            listDTO.add(dto);
        }

        return listDTO;
    }

    @Transactional
    public OrderDTO cancelOrder(Long id) {

        Order order = orderRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Pedido não encontrado"));
        if (order.getStatus().equals(OrderStatus.CANCELLED)) {
            throw new EntityInvalidDataException("Pedido já se encontra cancelado");
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
        productService.returnQuantityInStockAfterCanceledOrder(order.getItems());

        return new OrderDTO(order);
    }

    public BigDecimal calculateTotalPrice(List<OrderItemDTO> items) {

        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemDTO item : items) {
            Product product = productService.checkProductExistsById(item.getProductId());
            BigDecimal itemTotal = new BigDecimal(item.getQuantity()).multiply(product.getPrice());
            total = total.add(itemTotal);
        }

        return total;
    }

    private Order convertOrderPostDTOtoOrder(OrderPostDTO dto) {

        // create a list of OrderItem
        List<OrderItem> newListItems = orderItemService.convertOrderItemDTOtoOrderItemList(dto.getItems());

        // get customer from database
        Customer customer = customerRepository.findByCpf(dto.getCustomerCpf()).orElseThrow(
                () -> new ResourceNotFoundException("Cliente não encontrado: CPF: " + dto.getCustomerCpf()));

        Order order = new Order(newListItems);
        order.setDate(Instant.now());
        order.setTotalValue(calculateTotalPrice(dto.getItems()));
        order.setCustomer(customer);
        order.setStatus(OrderStatus.WAITING_PAYMENT);

        return order;
    }

    public OrderDTO findById(Long id) {
        
        Order entity = orderRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Pedido não encontrado"));

        return new OrderDTO(entity);
    }
}
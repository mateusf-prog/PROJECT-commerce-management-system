package br.com.mateus.commercemanagementsystem.services;

import br.com.mateus.commercemanagementsystem.dto.OrderCreatedDTO;
import br.com.mateus.commercemanagementsystem.dto.OrderDTO;
import br.com.mateus.commercemanagementsystem.dto.OrderItemDTO;
import br.com.mateus.commercemanagementsystem.dto.OrderPostDTO;
import br.com.mateus.commercemanagementsystem.exceptions.EntityInvalidDataException;
import br.com.mateus.commercemanagementsystem.exceptions.ResourceNotFoundException;
import br.com.mateus.commercemanagementsystem.model.*;
import br.com.mateus.commercemanagementsystem.model.enums.OrderStatus;
import br.com.mateus.commercemanagementsystem.repository.OrderRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final OrderItemService orderItemService;
    private final ProductService productService;
    private final PaymentService paymentService;

    public OrderService(OrderRepository orderRepository, CustomerService customerService,
                            OrderItemService orderItemService, ProductService productService,
                            PaymentService paymentService) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.orderItemService = orderItemService;
        this.productService = productService;
        this.paymentService = paymentService;
    }

    @Transactional
    public OrderCreatedDTO createOrder(OrderPostDTO dto) {

        customerService.findByCpf(dto.getCustomerCpf());
        Order order = convertOrderPostDTOtoOrder(dto);

        Order orderCreated = orderRepository.save(order);
        orderItemService.createOrderItem(orderCreated);
        orderCreated = orderRepository.save(orderCreated);

        /*Payment payment = paymentService.createPayment(order, dto.getPaymentType());
        order.setPayment(payment);*/

        return new OrderCreatedDTO(orderCreated, dto.getItems());
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
        List<OrderItem> newListItems = orderItemService.convertOrderItemDTOtoOrderItemList(dto.getItems());

        // get customer from database
        Customer customer = customerService.findByCpf(dto.getCustomerCpf());

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

// TODO: implementar logica para mudar o status de um pedido após o pagamento for concluído pela API DE PAGAMENTOS
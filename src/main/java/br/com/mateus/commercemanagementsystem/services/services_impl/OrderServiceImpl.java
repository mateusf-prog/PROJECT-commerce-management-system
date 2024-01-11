package br.com.mateus.commercemanagementsystem.services.services_impl;

import br.com.mateus.commercemanagementsystem.dto.OrderCreatedDTO;
import br.com.mateus.commercemanagementsystem.dto.OrderDTO;
import br.com.mateus.commercemanagementsystem.dto.OrderItemDTO;
import br.com.mateus.commercemanagementsystem.dto.OrderPostDTO;
import br.com.mateus.commercemanagementsystem.exceptions.EntityInvalidDataException;
import br.com.mateus.commercemanagementsystem.exceptions.ResourceNotFoundException;
import br.com.mateus.commercemanagementsystem.model.*;
import br.com.mateus.commercemanagementsystem.model.enums.OrderStatus;
import br.com.mateus.commercemanagementsystem.repository.OrderRepository;
import br.com.mateus.commercemanagementsystem.services.OrderService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerServiceImpl customerService;
    private final OrderItemServiceImpl orderItemService;
    private final ProductServiceImpl productService;
    private final PaymentServiceImpl paymentService;

    public OrderServiceImpl(OrderRepository orderRepository, CustomerServiceImpl customerService,
                            OrderItemServiceImpl orderItemService, ProductServiceImpl productService,
                            PaymentServiceImpl paymentService) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.orderItemService = orderItemService;
        this.productService = productService;
        this.paymentService = paymentService;
    }

    @Override
    @Transactional
    public OrderCreatedDTO createOrder(OrderPostDTO dto) {

        customerService.findByCpf(dto.getCustomerCpf());
        Order order = convertOrderPostDTOtoOrder(dto);

        for (OrderItemDTO item : dto.getItems()) {
            Product product = productService.checkProductExistsById(item.getProductId());
            OrderItem orderItem = new OrderItem(order, product, item.getQuantity(), product.getPrice());

            orderItemService.createOrderItem(orderItem);
        }

        Payment payment = paymentService.createPayment(order, dto.getPaymentType());
        order.setPayment(payment);

        order = orderRepository.save(order);
        return new OrderCreatedDTO(order, dto.getItems());
    }

    @Override
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

    @Override
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

        // get customer from database
        Customer customer = customerService.findByCpf(dto.getCustomerCpf());

        Order order = new Order();
        order.setDate(Instant.now());
        order.setTotalValue(calculateTotalPrice(dto.getItems()));
        order.setCustomer(customer);
        order.setStatus(OrderStatus.WAITING_PAYMENT);
        return order;
    }

    @Override
    public OrderDTO findById(Long id) {
        
        Order entity = orderRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Pedido não encontrado"));

        return new OrderDTO(entity);
    }
}

// TODO: implementar logica para mudar o status de um pedido após o pagamento for concluído pela API DE PAGAMENTOS
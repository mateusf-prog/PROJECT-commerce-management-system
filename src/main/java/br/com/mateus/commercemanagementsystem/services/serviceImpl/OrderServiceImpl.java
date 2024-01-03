package br.com.mateus.commercemanagementsystem.services.serviceImpl;

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
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerServiceImpl customerService;
    private final OrderItemServiceImpl orderItemService;
    private final PaymentServiceImpl paymentService;
    private final ProductServiceImpl productService;

    public OrderServiceImpl(OrderRepository orderRepository, CustomerServiceImpl customerService,
                            OrderItemServiceImpl orderItemService, PaymentServiceImpl paymentService,
                            ProductServiceImpl productService) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.orderItemService = orderItemService;
        this.paymentService = paymentService;
        this.productService = productService;
    }

    @Override
    @Transactional
    public OrderCreatedDTO createOrder(OrderPostDTO dto) {

        Customer customer = customerService.findByCpf(dto.getCustomerCpf());
        Order order = convertOrderPostDTOtoOrder(dto);
        order = orderRepository.save(order);

        for (OrderItemDTO item : dto.getItems()) {
            Product product = productService.checkProductExistsById(item.getProductId());
            OrderItem orderItem = new OrderItem(order, product, item.getQuantity(), product.getPrice());

            orderItemService.createOrderItem(orderItem);
        }

        return getOrderCreatedDTO(order, dto.getItems());
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {

        Optional<Order> orderQuery = orderRepository.findById(id);

        if (orderQuery.isEmpty()) {
            throw new ResourceNotFoundException("Pedido não encontrado. ID " + id);
        }

        return convertOrderToOrderDTO(orderQuery.get());
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDTO> findAllOrdersByClientCpf(String cpf) {

        Customer customer = customerService.findByCpf(cpf);

        List<Order> orders = orderRepository.findByCustomerCpf(cpf);
        if (orders.isEmpty()) {
            throw new ResourceNotFoundException("Cliente não possui nenhum pedido. CPF + " + cpf);
        }

        List<OrderDTO> listOrdersDTO = new ArrayList<>();
        for (Order order : orders) {
            listOrdersDTO.add(convertOrderToOrderDTO(order));
        }

        return listOrdersDTO;
    }

    @Transactional(readOnly = true)
    public List<OrderDTO> findAll() {

        List<Order> list = orderRepository.findAll();
        if (list.isEmpty()) {
            throw new ResourceNotFoundException("Lista vazia!");
        }

        List<OrderDTO> listDTO = new ArrayList<OrderDTO>();
        for (Order order : list) {
            listDTO.add(convertOrderToOrderDTO(order));
        }

        return listDTO;
    }

    @Transactional
    public OrderDTO cancelOrder(Long id) {
        // todo: retornar a lista de items deste pedido também
        Order order = orderRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Pedido não encontrado!"));
        if (order.getStatus().equals(OrderStatus.CANCELLED)) {
            throw new EntityInvalidDataException("Pedido já se encontra cancelado");
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
        productService.returnQuantityInStockAfterCanceledOrder(order.getItems());

        return convertOrderToOrderDTO(order);
    }

    private static OrderCreatedDTO getOrderCreatedDTO(Order order, List<OrderItemDTO> list) {
        OrderCreatedDTO orderCreatedDTO = new OrderCreatedDTO(list);
        orderCreatedDTO.setOrderId(order.getId());
        orderCreatedDTO.setDate(order.getDate());
        orderCreatedDTO.setCustomer(order.getCustomer().getName());
        orderCreatedDTO.setStatus(order.getStatus());
        orderCreatedDTO.setCpf(order.getCustomer().getCpf());
        orderCreatedDTO.setTotalValue(order.getTotalValue());

        return orderCreatedDTO;
    }

    private OrderDTO convertOrderToOrderDTO(Order order) {

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setCustomerCpf(order.getCustomer().getCpf());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setTotalValue(order.getTotalValue());
        orderDTO.setDate(order.getDate());

        return orderDTO;
    }

    @Override
    public BigDecimal calculateTotalPrice(List<OrderItemDTO> items) {

        BigDecimal productPrice;
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemDTO item : items) {
            Product product = productService.checkProductExistsById(item.getProductId());
            productPrice = product.getPrice();
            total = total.add(productPrice.multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        return total;
    }

    private Order convertOrderPostDTOtoOrder(OrderPostDTO dto) {

        // get customer from database
        Customer customer = customerService.findByCpf(dto.getCustomerCpf());
        if (customer == null) {
            throw new ResourceNotFoundException("Cliente não encontrado.");
        }

        Order order = new Order();
        order.setDate(Instant.now());
        order.setTotalValue(calculateTotalPrice(dto.getItems()));
        order.setCustomer(customer);
        order.setStatus(OrderStatus.WAITING_PAYMENT);

        return order;
    }
}

// TODO: implementar logica para mudar o status de um pedido após o pagamento for concluído pela API DE PAGAMENTOS
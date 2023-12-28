package br.com.mateus.commercemanagementsystem.services.serviceImpl;

import br.com.mateus.commercemanagementsystem.dto.OrderCreatedDTO;
import br.com.mateus.commercemanagementsystem.dto.OrderDTO;
import br.com.mateus.commercemanagementsystem.exceptions.EntityMissingDependencyException;
import br.com.mateus.commercemanagementsystem.exceptions.EntityNotFoundException;
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
    public OrderCreatedDTO createOrder(OrderDTO orderDTO) {

        Order order = convertOrderDTOtoOrder(orderDTO);

        Order orderSaved = orderRepository.save(order);
        for (OrderItem item : orderDTO.getOrderItems()) {
            orderItemService.createOrderItem(item);
        }
        orderDTO.setId(orderSaved.getId());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setTotalValue(order.getTotalValue());
        orderDTO.setDate(Instant.now());

        return getOrderCreatedDTO(orderDTO, order);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {

        Optional<Order> orderQuery = orderRepository.findById(id);

        if (orderQuery.isEmpty()) {
            throw new EntityNotFoundException("Pedido não encontrado. ID " + id);
        }

        return convertOrderToOrderDTO(orderQuery.get());
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDTO> findAllOrdersByClientCpf(String cpf) {

        Customer customer = customerService.findByCpf(cpf);

        if (customer == null) {
            throw new EntityNotFoundException("Cliente não encontrado. CPF " + cpf);
        }

        List<Order> orders = orderRepository.findByCustomerCpf(cpf);
        if (orders.isEmpty()) {
            throw new EntityNotFoundException("Cliente não possui nenhum pedido. CPF + " + cpf);
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
            throw new EntityNotFoundException("Lista vazia!");
        }

        List<OrderDTO> listDTO = new ArrayList<OrderDTO>();
        for (Order order : list) {
            listDTO.add(convertOrderToOrderDTO(order));
        }

        return listDTO;
    }

    @Transactional
    public OrderDTO cancelOrder(Long id) {

        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty()) {
            throw new EntityNotFoundException("Pedido não encontrado!");
        }
        order.get().setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order.get());
        // todo: implementar logica para retornar a quantidade de produtos no estoque após o pedido ser cancelado

        return convertOrderToOrderDTO(order.get());
    }

    private static OrderCreatedDTO getOrderCreatedDTO(OrderDTO orderDTO, Order order) {
        OrderCreatedDTO orderCreatedDTO = new OrderCreatedDTO();
        orderCreatedDTO.setOrderId(orderDTO.getId());
        orderCreatedDTO.setDate(orderDTO.getDate());
        orderCreatedDTO.setCustomer(order.getCustomer().getName());
        orderCreatedDTO.setStatus(orderDTO.getStatus());
        orderCreatedDTO.setCpf(orderDTO.getCustomerCpf());
        orderCreatedDTO.setTotalValue(orderDTO.getTotalValue());
        orderCreatedDTO.setListItems(orderDTO.getOrderItems());

        return orderCreatedDTO;
    }

    private OrderDTO convertOrderToOrderDTO(Order order) {

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setCustomerCpf(order.getCustomer().getCpf());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setTotalValue(order.getTotalValue());

        return orderDTO;
    }

    @Override
    public BigDecimal calculateTotalPrice(OrderDTO order) {

        BigDecimal productPrice;
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItem item : order.getOrderItems()) {
            Product product = productService.checkProductExistsByName(item.getProduct().getName());
            productPrice = product.getPrice();
            total = total.add(productPrice.multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        return total;
    }

    private Order convertOrderDTOtoOrder(OrderDTO orderDTO) {

        // get customer from database
        Customer customer = customerService.findByCpf(orderDTO.getCustomerCpf());
        if (customer == null) {
            throw new EntityNotFoundException("Cliente não encontrado.");
        }

        Order order = new Order();
        order.setDate(Instant.now());
        order.setTotalValue(calculateTotalPrice(orderDTO));
        order.setCustomer(customer);
        order.setStatus(OrderStatus.WAITING_PAYMENT);

        return order;
    }
}

// todo: verificar logica da lista de items do json para criação do order
// TODO: implementar logica para mudar o status de um pedido após o pagamento for concluído pela API DE PAGAMENTOS
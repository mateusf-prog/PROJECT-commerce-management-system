package br.com.mateus.commercemanagementsystem.service.serviceImpl;

import br.com.mateus.commercemanagementsystem.dto.OrderDTO;
import br.com.mateus.commercemanagementsystem.exceptions.EntityMissingDependencyException;
import br.com.mateus.commercemanagementsystem.exceptions.EntityNotFoundException;
import br.com.mateus.commercemanagementsystem.exceptions.order.*;
import br.com.mateus.commercemanagementsystem.model.Client;
import br.com.mateus.commercemanagementsystem.model.Order;
import br.com.mateus.commercemanagementsystem.model.OrderItem;
import br.com.mateus.commercemanagementsystem.model.Payment;
import br.com.mateus.commercemanagementsystem.model.enums.PaymentStatus;
import br.com.mateus.commercemanagementsystem.repository.OrderRepository;
import br.com.mateus.commercemanagementsystem.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ClientServiceImpl clientService;
    private final OrderItemServiceImpl orderItemService;
    private final PaymentServiceImpl paymentService;

    public OrderServiceImpl(OrderRepository orderRepository, ClientServiceImpl clientService,
                            OrderItemServiceImpl orderItemService, PaymentServiceImpl paymentService) {
        this.orderRepository = orderRepository;
        this.clientService = clientService;
        this.orderItemService = orderItemService;
        this.paymentService = paymentService;
    }


    @Override
    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {

        checkValidations(orderDTO);

        Client client = clientService.findByCpf(orderDTO.getClientCpf());
        if(client == null) {
            throw new EntityNotFoundException("Cliente não encontrado.");
        }

        for (OrderItem orderItem : orderDTO.getOrderItems()) {
            orderItemService.checkAllValidates(orderItem);
        }

        List<OrderItem> orderItems = orderDTO.getOrderItems();

        // instantiating objects
        Order order = new Order(orderItems);
        order.setDate(LocalDateTime.now());
        order.setTotalValue(calculateTotalPrice(orderDTO));
        order.setClient(client);

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentType(orderDTO.getPaymentType());
        payment.setValue(calculateTotalPrice(orderDTO));
        payment.setDate(order.getDate());
        payment.setStatus(PaymentStatus.PENDING);

        paymentService.createPayment(payment);
        order.setPayment(payment);
        orderRepository.save(order);
        return orderDTO;
    }

    @Override
    public Order updateOrder(Order order) {
        return null;
    }

    @Override
    public String deleteById(Long id) {
        return null;
    }

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public String addItem(Order order, OrderItem item) {
        return null;
    }

    @Override
    public String removeItem(OrderDTO order, OrderItem item) {
        return null;
    }

    @Override
    public String setPayment(Order order, Payment payment) {
        return null;
    }

    public BigDecimal calculateTotalPrice(OrderDTO order) {

        BigDecimal total = BigDecimal.ZERO;

        for (OrderItem item : order.getOrderItems()) {
            total = total.add(item.getPrice());
        }

        return total;
    }

    // validations

    public void checkValidations(OrderDTO orderDTO) {

        if (orderDTO.getOrderItems().isEmpty()) {
            throw new EntityMissingDependencyException("O pedido precisa ter pelo menos um item!");
        }
        if (orderDTO.getPaymentType() == null) {
            throw new EntityMissingDependencyException("O pedido precisa ter um pagamento associado!");
        }
        if (orderDTO.getClientCpf() == null) {
            throw new EntityMissingDependencyException("O pedido precisa ter um cliente!");
        }
    }
}

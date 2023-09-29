package br.com.mateus.commercemanagementsystem.service.serviceImpl;

import br.com.mateus.commercemanagementsystem.dto.OrderDTO;
import br.com.mateus.commercemanagementsystem.dto.OrderItemDTO;
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
import java.util.ArrayList;
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
        if(clientService.findByCpf(orderDTO.getClientCpf()) == null) {
            throw new EntityNotFoundException("Cliente não encontrado.");
        }

        // instantiating objects
        Order order = new Order(orderDTO.getOrderItems());
        order.setDate(LocalDateTime.now());
        order.setTotalValue(calculateTotalPrice(orderDTO));
        order.setClient(client);

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentType(orderDTO.getPaymentType());
        payment.setValue(order.getTotalValue());
        payment.setStatus(PaymentStatus.PENDING);

        // persist
        paymentService.createPayment(payment);
        order.setPayment(payment);
        Order orderSaved = orderRepository.save(order);
        for (OrderItem item : order.getOrderItems()) {
            orderItemService.createOrderItem(item);
        }
        orderDTO.setId(orderSaved.getId());
        return orderDTO;
    }

    @Override
    public OrderDTO updateOrder(OrderDTO orderDTO) {
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
    public String setPayment(Order order, Payment payment) {
        return null;
    }

    public BigDecimal calculateTotalPrice(OrderDTO order) {

        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem item : order.getOrderItems()) {
            total = total.add(item.getPriceUnit().multiply(BigDecimal.valueOf(item.getQuantity())));
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

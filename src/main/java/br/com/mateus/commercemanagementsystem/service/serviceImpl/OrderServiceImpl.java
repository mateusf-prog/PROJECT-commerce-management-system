package br.com.mateus.commercemanagementsystem.service.serviceImpl;

import br.com.mateus.commercemanagementsystem.dto.OrderDTO;
import br.com.mateus.commercemanagementsystem.exceptions.EntityMissingDependencyException;
import br.com.mateus.commercemanagementsystem.exceptions.EntityNotFoundException;
import br.com.mateus.commercemanagementsystem.model.*;
import br.com.mateus.commercemanagementsystem.model.enums.PaymentStatus;
import br.com.mateus.commercemanagementsystem.repository.OrderRepository;
import br.com.mateus.commercemanagementsystem.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ClientServiceImpl clientService;
    private final OrderItemServiceImpl orderItemService;
    private final PaymentServiceImpl paymentService;
    private final ProductServiceImpl productService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ClientServiceImpl clientService,
                            OrderItemServiceImpl orderItemService, PaymentServiceImpl paymentService,
                            ProductServiceImpl productService) {
        this.orderRepository = orderRepository;
        this.clientService = clientService;
        this.orderItemService = orderItemService;
        this.paymentService = paymentService;
        this.productService = productService;
    }


    @Override
    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {

        checkValidations(orderDTO);
        Order order = convertOrderDTOtoOrder(orderDTO);

        // persist Order, Payment and OrderItems
        paymentService.createPayment(order.getPayment());
        Order orderSaved = orderRepository.save(order);
        for (OrderItem item : order.getOrderItems()) {
            orderItemService.createOrderItem(item);
        }
        orderDTO.setId(orderSaved.getId());
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO updateOrder(OrderDTO orderDTO) {

        Optional<Order> orderExists = orderRepository.findById(orderDTO.getId());

        if (orderExists.isEmpty()) {
            throw new EntityNotFoundException("Pedido não encontrado - ID " + orderDTO.getId());
        }

        Order order = convertOrderDTOtoOrder(orderDTO);
        // check validations of the object
        checkValidations(orderDTO);
        for (OrderItem item : orderDTO.getOrderItems()) {
            orderItemService.updateOrderItem(item);
        }

        orderRepository.save(order);
        return orderDTO;
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

        BigDecimal productPrice = BigDecimal.ZERO;
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItem item : order.getOrderItems()) {
            Optional<Product> product = productService.findByName(item.getProductName());
            if (product.isEmpty()) {
                throw new EntityNotFoundException("Produto não encontrado - " + item.getProductName());
            }
            productPrice = product.get().getPrice();
            total = total.add(productPrice.multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        return total;
    }

    public Order convertOrderDTOtoOrder(OrderDTO orderDTO) {

        // check if cpf exists in the database
        Client client = clientService.findByCpf(orderDTO.getClientCpf());
        if(client == null) {
            throw new EntityNotFoundException("Cliente não encontrado.");
        }

        // instantiating objects Order and Payment
        Order order = new Order(orderDTO.getOrderItems());
        order.setDate(LocalDateTime.now());
        order.setTotalValue(calculateTotalPrice(orderDTO));
        order.setClient(client);

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentType(orderDTO.getPaymentType());
        payment.setValue(order.getTotalValue());
        payment.setStatus(PaymentStatus.PENDING);

        order.setPayment(payment);
        return order;
    }

    // validations

    public void checkValidations(OrderDTO orderDTO) {

        if (orderDTO.getOrderItems().isEmpty()) {
            throw new EntityMissingDependencyException("O pedido precisa ter pelo menos um item!");
        }
        if (orderDTO.getPaymentType() == null) {
            throw new EntityMissingDependencyException("O pedido precisa ter um tipo de pagamento associado!");
        }
        if (orderDTO.getClientCpf() == null) {
            throw new EntityMissingDependencyException("O pedido precisa ter um cliente!");
        }
    }
}

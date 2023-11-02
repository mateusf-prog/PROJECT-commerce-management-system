package br.com.mateus.commercemanagementsystem.service.serviceImpl;

import br.com.mateus.commercemanagementsystem.dto.OrderDTO;
import br.com.mateus.commercemanagementsystem.exceptions.EntityMissingDependencyException;
import br.com.mateus.commercemanagementsystem.exceptions.EntityNotFoundException;
import br.com.mateus.commercemanagementsystem.model.*;
import br.com.mateus.commercemanagementsystem.model.enums.OrderStatus;
import br.com.mateus.commercemanagementsystem.model.enums.PaymentStatus;
import br.com.mateus.commercemanagementsystem.repository.OrderRepository;
import br.com.mateus.commercemanagementsystem.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    private final ProductServiceImpl productService;

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
        paymentService.createPayment(order);
        Order orderSaved = orderRepository.save(order);
        for (OrderItem item : order.getOrderItems()) {
            orderItemService.createOrderItem(item);
        }
        orderDTO.setId(orderSaved.getId());
        orderDTO.setStatus(order.getStatus());

        // create payment object
        Payment payment = paymentService.createPayment(order);
        payment.setPaymentType(orderDTO.getPaymentType());

        // set payment object to order
        order.setPayment(payment);

        return orderDTO;
    }

    @Override
    public OrderDTO findById(Long id) {

        Optional<Order> orderQuery = orderRepository.findById(id);

        if (orderQuery.isEmpty()) {
            throw new EntityNotFoundException("Pedido não encontrado. ID " + id);
        }

        OrderDTO orderDTO = new OrderDTO(orderQuery.get().getOrderItems());
        orderDTO.setStatus(orderQuery.get().getStatus());
        orderDTO.setId(orderQuery.get().getId());
        orderDTO.setPaymentType(orderQuery.get().getPayment().getPaymentType());
        orderDTO.setTotalValue(orderQuery.get().getTotalValue());
        orderDTO.setClientCpf(orderQuery.get().getClient().getCpf());

        return orderDTO;
    }

    @Override
    public List<OrderDTO> findAllOrdersByClientCpf(String cpf) {

        Client client = clientService.findByCpf(cpf);

        if(client == null) {
            throw new EntityNotFoundException("Cliente não encontrado. CPF " + cpf);
        }

        List<Order> orders = orderRepository.findByClientCpf(cpf);

        if(orders.isEmpty()) {
            throw new EntityNotFoundException("Cliente não possui nenhum pedido. CPF + " + cpf);
        }

        List<OrderDTO> listOrdersDTO = new ArrayList<>();
        for (Order order : orders) {
            listOrdersDTO.add(convertOrderToOrderDTO(order));
        }

        return listOrdersDTO;
    }

    public List<OrderDTO> findAll() {

        List<Order> list = orderRepository.findAll();

        if(list.isEmpty()) {
            throw new EntityNotFoundException("Lista vazia!");
        }

        List<OrderDTO> listDTO = new ArrayList<OrderDTO>();
        for (Order order : list) {
            listDTO.add(convertOrderToOrderDTO(order));
        }

        return listDTO;
    }

    @Override
    public OrderDTO convertOrderToOrderDTO(Order order) {

        OrderDTO orderDTO = new OrderDTO(order.getOrderItems());
        orderDTO.setId(order.getId());
        orderDTO.setClientCpf(order.getClient().getCpf());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setPaymentType(order.getPayment().getPaymentType());
        orderDTO.setTotalValue(order.getTotalValue());

        return orderDTO;
    }
    
    @Override
    public BigDecimal calculateTotalPrice(OrderDTO order) {

        BigDecimal productPrice;
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

    @Override
    public Order convertOrderDTOtoOrder(OrderDTO orderDTO) {

        // check if cpf exists in the database and get client object    
        Client client = clientService.findByCpf(orderDTO.getClientCpf());
        if(client == null) {
            throw new EntityNotFoundException("Cliente não encontrado.");
        }

        // create order object
        Order order = new Order(orderDTO.getOrderItems());
        order.setDate(LocalDateTime.now());
        order.setTotalValue(calculateTotalPrice(orderDTO));
        order.setClient(client);
        order.setStatus(OrderStatus.WAITING_PAYMENT);

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

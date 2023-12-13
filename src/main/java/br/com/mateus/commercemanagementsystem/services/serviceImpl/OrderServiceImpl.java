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

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public OrderCreatedDTO createOrder(OrderDTO orderDTO) {

        checkValidations(orderDTO);
        Order order = convertOrderDTOtoOrder(orderDTO);

        // create Payment before persist Order and create OrderItems
        paymentService.createPayment(order);
        Order orderSaved = orderRepository.save(order);
        for (OrderItem item : order.getOrderItems()) {
            orderItemService.createOrderItem(item);
        }
        orderDTO.setId(orderSaved.getId());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setTotalValue(order.getTotalValue());
        orderDTO.setDate(Instant.now());

        // create payment with order object
        Payment payment = paymentService.createPayment(order);
        payment.setPaymentType(orderDTO.getPaymentType());

        // set payment object to order
        order.setPayment(payment);

         return getOrderCreatedDTO(orderDTO, order, payment);
    }
    /**
     * This function creates an OrderCreatedDTO object from given OrderDTO,
     * Order, and Payment objects.
     *
     * @param orderDTO   The OrderDTO object to get details from.
     * @param order      The Order object to get the customer name from.
     * @param payment    The Payment object to get the external API payment ID from.
     */
    private static OrderCreatedDTO getOrderCreatedDTO(OrderDTO orderDTO, Order order, Payment payment) {
        OrderCreatedDTO orderCreatedDTO = new OrderCreatedDTO();
        orderCreatedDTO.setOrderId(orderDTO.getId());
        orderCreatedDTO.setDate(orderDTO.getDate());
        orderCreatedDTO.setCustomer(order.getCustomer().getName());
        orderCreatedDTO.setStatus(orderDTO.getStatus());
        orderCreatedDTO.setCpf(orderDTO.getCustomerCpf());
        orderCreatedDTO.setPaymentType(orderDTO.getPaymentType());
        orderCreatedDTO.setValue(orderDTO.getTotalValue());
        orderCreatedDTO.setListItems(orderDTO.getOrderItems());
        orderCreatedDTO.setPaymentId(payment.getIdApiExternal());

        return orderCreatedDTO;
    }

    @Override
    public OrderDTO findById(Long id) {

        Optional<Order> orderQuery = orderRepository.findById(id);

        if (orderQuery.isEmpty()) {
            throw new EntityNotFoundException("Pedido não encontrado. ID " + id);
        }

        OrderDTO orderDTO = convertOrderToOrderDTO(orderQuery.get());
        return orderDTO;
    }

    @Override
    public List<OrderDTO> findAllOrdersByClientCpf(String cpf) {

        Customer customer = customerService.findByCpf(cpf);

        if(customer == null) {
            throw new EntityNotFoundException("Cliente não encontrado. CPF " + cpf);
        }

        List<Order> orders = orderRepository.findByCustomerCpf(cpf);
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

    private OrderDTO convertOrderToOrderDTO(Order order) {

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setCustomerCpf(order.getCustomer().getCpf());
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

    private Order convertOrderDTOtoOrder(OrderDTO orderDTO) {

        // get customer from database
        Customer customer = customerService.findByCpf(orderDTO.getCustomerCpf());
        if(customer == null) {
            throw new EntityNotFoundException("Cliente não encontrado.");
        }

        Order order = new Order();
        order.setDate(Instant.now());
        order.setTotalValue(calculateTotalPrice(orderDTO));
        order.setCustomer(customer);
        order.setStatus(OrderStatus.WAITING_PAYMENT);

        return order;
    }

    // checking if items is not empty, payment type is not null and customer exists
    private void checkValidations(OrderDTO orderDTO) {

        if (orderDTO.getOrderItems().isEmpty()) {
            throw new EntityMissingDependencyException("O pedido precisa ter pelo menos um item!");
        }
        if (orderDTO.getPaymentType() == null) {
            throw new EntityMissingDependencyException("O pedido precisa ter um tipo de pagamento associado!");
        }
        if (orderDTO.getCustomerCpf() == null) {
            throw new EntityMissingDependencyException("O pedido precisa ter um cliente!");
        }
    }
}

// TODO: implementar logica para mudar o status de um pedido após o pagamento for concluído pela API DE PAGAMENTOS
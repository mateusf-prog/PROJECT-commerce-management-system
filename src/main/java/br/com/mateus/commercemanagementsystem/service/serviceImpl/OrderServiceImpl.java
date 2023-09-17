package br.com.mateus.commercemanagementsystem.service.serviceImpl;

import br.com.mateus.commercemanagementsystem.exceptions.order.OrderNotContainClientException;
import br.com.mateus.commercemanagementsystem.exceptions.order.OrderNotContainItemsException;
import br.com.mateus.commercemanagementsystem.exceptions.order.OrderNotContainPaymentException;
import br.com.mateus.commercemanagementsystem.exceptions.order.OrderNotFoundException;
import br.com.mateus.commercemanagementsystem.model.Order;
import br.com.mateus.commercemanagementsystem.model.OrderItem;
import br.com.mateus.commercemanagementsystem.model.Payment;
import br.com.mateus.commercemanagementsystem.model.enums.PaymentType;
import br.com.mateus.commercemanagementsystem.repository.OrderRepository;
import br.com.mateus.commercemanagementsystem.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    @Transactional
    public Order createOrder(Order order) {

        try {
            checkValidations(order);
            orderRepository.save(order);
        } catch (Exception e) {
            throw e;
        }

        return order;
    }

    @Override
    @Transactional
    public Order updateOrder(Order order) {

        Optional<Order> orderQuery = findById(order.getId());

        if (orderQuery.isPresent()) {
            try {
                checkValidations(order);
                orderRepository.save(order);
            } catch (Exception e) {
                throw e;
            }
        } else {
            throw new OrderNotFoundException("Order id not found!");
        }

        return order;
    }

    @Override
    @Transactional
    public String deleteById(Long id) {

        Optional<Order> order = findById(id);

        if (order.isEmpty()) {
            throw new OrderNotFoundException("Order not found!");
        } else {
            orderRepository.deleteById(id);
            return "Order deleted!";
        }
    }

    @Override
    public Optional<Order> findById(Long id) {

        Optional<Order> order = orderRepository.findById(id);

        if (order.isEmpty()) {
            throw new OrderNotFoundException("Order not found!");
        } else {
            return order;
        }
    }

    @Override
    public void addItem(Order order, OrderItem item) {

    }

    @Override
    public void removeItem(OrderItem item) {

    }

    @Override
    public void setPayment(Payment payment) {

    }

    @Override
    public String changePaymentType(Order order, PaymentType paymentType) {
        return null;
    }

    // validations

    public void checkValidations(Order order) {

        if (order.getOrderItems().isEmpty()) {
            throw new OrderNotContainItemsException("O pedido precisa ter pelo menos um item!");
        }
        if (order.getPayment() == null) {
            throw new OrderNotContainPaymentException("O pedido precisa ter um pagamento associado!");
        }
        if (order.getClient() == null) {
            throw new OrderNotContainClientException("O pedido precisa ter um cliente!");
        }
    }
}

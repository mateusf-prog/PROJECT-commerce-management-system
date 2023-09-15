package br.com.mateus.commercemanagementsystem.service.serviceImpl;

import br.com.mateus.commercemanagementsystem.exceptions.order.OrderNotContainClientException;
import br.com.mateus.commercemanagementsystem.exceptions.order.OrderNotContainItemsException;
import br.com.mateus.commercemanagementsystem.exceptions.order.OrderNotContainPaymentException;
import br.com.mateus.commercemanagementsystem.model.Order;
import br.com.mateus.commercemanagementsystem.model.OrderItem;
import br.com.mateus.commercemanagementsystem.model.Payment;
import br.com.mateus.commercemanagementsystem.model.enums.PaymentType;
import br.com.mateus.commercemanagementsystem.repository.OrderRepository;
import br.com.mateus.commercemanagementsystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order createOrder(Order order) {
        return null;
    }

    @Override
    public Order readOrder(Order order) {
        return null;
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
    public Order findById(Long id) {
        return null;
    }

    @Override
    public void addItem(OrderItem item) {

    }

    @Override
    public void removeItem(OrderItem item) {

    }

    @Override
    public void setPayment(Payment payment) {

    }

    @Override
    public String changePaymentType(PaymentType paymentType) {
        return null;
    }

    // validations

    public Optional<Exception> checkValidations(Order order) {

        if (order.getOrderItems().isEmpty()) {
            throw new OrderNotContainItemsException("O pedido precisa ter pelo menos um item!");
        }
        if (order.getPayment() == null) {
            throw new OrderNotContainPaymentException("O pedido precisa ter um pagamento associado!");
        }
        if (order.getClient() == null) {
            throw new OrderNotContainClientException("O pedido precisa ter um cliente!");
        }

        return Optional.empty();
    }
}

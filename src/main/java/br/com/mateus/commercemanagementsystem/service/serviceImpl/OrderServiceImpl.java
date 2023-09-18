package br.com.mateus.commercemanagementsystem.service.serviceImpl;

import br.com.mateus.commercemanagementsystem.exceptions.order.*;
import br.com.mateus.commercemanagementsystem.model.Order;
import br.com.mateus.commercemanagementsystem.model.OrderItem;
import br.com.mateus.commercemanagementsystem.model.Payment;
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
            throw new OrderNotFoundException("Order ID not found!");
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
    @Transactional
    public String addItem(Order order, OrderItem item) {

        if (order != null && item != null) {
            if (!order.getOrderItems().contains(item)) {
                order.getOrderItems().add(item);
                checkValidations(order);
                orderRepository.save(order);
                return "Produto adicionado!"; // falta salvar o novo orderitem no banco de dados
            } else {
                throw new AddOrRemoveOrderItemInvalidException("Item já existe no pedido!");
            }
        } else {
            throw new AddOrRemoveOrderItemInvalidException("Verifique os items e tente novamente!");
        }
    }

    @Override
    @Transactional
    public String removeItem(Order order, OrderItem item) {

        if(order != null && item != null) {
            if(order.getOrderItems().contains(item)) {
                order.getOrderItems().remove(item);  // falta salvar o novo orderitem no banco de dados
                checkValidations(order);
                orderRepository.save(order);
                return "Produto removido!";
            } else {
                throw new AddOrRemoveOrderItemInvalidException("Item não existe no pedido!");
            }
        } else {
            throw new AddOrRemoveOrderItemInvalidException("Verifique os items e tente novamente!");
        }
    }

    @Override
    public String setPayment(Order order, Payment payment) {

        if (payment != null) {
            order.setPayment(payment);
            checkValidations(order);
            updateOrder(order);
            return "Pagamento alterado!"; // falta salvar o novo payment no banco de dados
        } else {
            throw new InvalidPaymentChangeException("O novo tipo de pagamento deve ser válido!");
        }
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

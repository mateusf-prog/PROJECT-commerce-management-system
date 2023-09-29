package br.com.mateus.commercemanagementsystem.service;

import br.com.mateus.commercemanagementsystem.dto.OrderDTO;
import br.com.mateus.commercemanagementsystem.model.Client;
import br.com.mateus.commercemanagementsystem.model.Order;
import br.com.mateus.commercemanagementsystem.model.OrderItem;
import br.com.mateus.commercemanagementsystem.model.Payment;
import br.com.mateus.commercemanagementsystem.model.enums.PaymentType;

import java.util.Optional;

public interface OrderService {

    OrderDTO createOrder(OrderDTO orderDTO);
    OrderDTO updateOrder(OrderDTO orderDT);
    String deleteById(Long id);
    Optional<Order> findById(Long id);

    String setPayment(Order order, Payment payment);
}

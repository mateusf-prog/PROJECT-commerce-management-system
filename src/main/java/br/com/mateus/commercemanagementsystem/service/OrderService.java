package br.com.mateus.commercemanagementsystem.service;

import br.com.mateus.commercemanagementsystem.dto.OrderDTO;
import br.com.mateus.commercemanagementsystem.model.Order;
import br.com.mateus.commercemanagementsystem.model.Payment;

public interface OrderService {

    OrderDTO createOrder(OrderDTO orderDTO);
    OrderDTO findById(Long id);

    String setPayment(Order order, Payment payment);
}

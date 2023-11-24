package br.com.mateus.commercemanagementsystem.service;

import br.com.mateus.commercemanagementsystem.dto.OrderDTO;
import br.com.mateus.commercemanagementsystem.model.Order;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {

    OrderDTO createOrder(OrderDTO orderDTO);
    OrderDTO findById(Long id);
    List<OrderDTO> findAllOrdersByClientCpf(String cpf);

    BigDecimal calculateTotalPrice(OrderDTO order);
}

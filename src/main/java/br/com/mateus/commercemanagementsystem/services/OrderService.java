package br.com.mateus.commercemanagementsystem.services;

import br.com.mateus.commercemanagementsystem.dto.OrderCreatedDTO;
import br.com.mateus.commercemanagementsystem.dto.OrderDTO;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {

    OrderCreatedDTO createOrder(OrderDTO orderDTO);
    OrderDTO findById(Long id);
    List<OrderDTO> findAllOrdersByClientCpf(String cpf);
    List<OrderDTO> findAll();
    OrderDTO cancelOrder(Long id);

    BigDecimal calculateTotalPrice(OrderDTO order);
}

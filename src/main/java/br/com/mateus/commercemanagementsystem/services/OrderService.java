package br.com.mateus.commercemanagementsystem.services;

import br.com.mateus.commercemanagementsystem.dto.OrderCreatedDTO;
import br.com.mateus.commercemanagementsystem.dto.OrderDTO;
import br.com.mateus.commercemanagementsystem.dto.OrderItemDTO;
import br.com.mateus.commercemanagementsystem.dto.OrderPostDTO;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {

    OrderCreatedDTO createOrder(OrderPostDTO dto);
    List<OrderDTO> findByCustomerCpf(String cpf);
    List<OrderDTO> findAll();
    OrderDTO cancelOrder(Long id);

    BigDecimal calculateTotalPrice(List<OrderItemDTO> items);
}

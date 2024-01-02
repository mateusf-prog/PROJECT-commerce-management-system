package br.com.mateus.commercemanagementsystem.services;

import br.com.mateus.commercemanagementsystem.dto.OrderCreatedDTO;
import br.com.mateus.commercemanagementsystem.dto.OrderDTO;
import br.com.mateus.commercemanagementsystem.dto.OrderItemDTO;
import br.com.mateus.commercemanagementsystem.dto.OrderPostDTO;
import br.com.mateus.commercemanagementsystem.model.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {

    OrderCreatedDTO createOrder(OrderPostDTO dto);
    OrderDTO findById(Long id);
    List<OrderDTO> findAllOrdersByClientCpf(String cpf);
    List<OrderDTO> findAll();
    OrderDTO cancelOrder(Long id);

    BigDecimal calculateTotalPrice(List<OrderItemDTO> items);
}

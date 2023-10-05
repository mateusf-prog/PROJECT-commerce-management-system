package br.com.mateus.commercemanagementsystem.dto;

import br.com.mateus.commercemanagementsystem.model.OrderItem;
import br.com.mateus.commercemanagementsystem.model.enums.OrderStatus;
import br.com.mateus.commercemanagementsystem.model.enums.PaymentType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDTO {

    private Long id;
    private BigDecimal totalValue;
    private String clientCpf;
    private PaymentType paymentType;
    private OrderStatus status;
    private List<OrderItem> orderItems;
}

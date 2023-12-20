package br.com.mateus.commercemanagementsystem.dto;

import br.com.mateus.commercemanagementsystem.model.OrderItem;
import br.com.mateus.commercemanagementsystem.model.enums.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderCreatedDTO {

    private Long orderId;
    private OrderStatus status;
    private String customer;
    private String cpf;
    private BigDecimal totalValue;
    private Instant date;
    private List<OrderItem> listItems;
}

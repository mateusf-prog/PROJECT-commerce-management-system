package br.com.mateus.commercemanagementsystem.dto;

import br.com.mateus.commercemanagementsystem.model.OrderItem;
import br.com.mateus.commercemanagementsystem.model.enums.OrderStatus;
import br.com.mateus.commercemanagementsystem.model.enums.PaymentType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderDTO {

    private Long id;
    private BigDecimal totalValue;
    private String clientCpf;
    private PaymentType paymentType;
    private OrderStatus status;

    @Setter(AccessLevel.NONE)
    private List<OrderItem> orderItems;

    public OrderDTO(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}

package br.com.mateus.commercemanagementsystem.dto;

import br.com.mateus.commercemanagementsystem.model.Order;
import br.com.mateus.commercemanagementsystem.model.enums.OrderStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class OrderCreatedDTO {

    private Long orderId;
    private OrderStatus status;
    private String customerName;
    private String cpf;
    private BigDecimal totalValue;
    private Instant date;
    @Setter(AccessLevel.NONE)
    private List<OrderItemDTO> listItems = new ArrayList<>();

    public OrderCreatedDTO(Order entity, List<OrderItemDTO> items) {
        orderId = entity.getId();
        status = entity.getStatus();
        customerName = entity.getCustomer().getName();
        cpf = entity.getCustomer().getCpf();
        totalValue = entity.getTotalValue();
        date = entity.getDate();
        listItems = items;
    }
}

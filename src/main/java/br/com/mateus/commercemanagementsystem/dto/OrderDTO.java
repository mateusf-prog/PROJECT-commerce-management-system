package br.com.mateus.commercemanagementsystem.dto;

import br.com.mateus.commercemanagementsystem.model.Order;
import br.com.mateus.commercemanagementsystem.model.OrderItem;
import br.com.mateus.commercemanagementsystem.model.enums.OrderStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.br.CPF;

@Data
@NoArgsConstructor
public class OrderDTO {

    private Long id;

    @DecimalMin(value = "0.0", inclusive = false, message = "Valor total deve ser maior que zero!")
    private BigDecimal totalValue;

    @CPF(message = "CPF inválido!")
    private String customerCpf;
    private OrderStatus status;
    private Instant date;

    @Setter(AccessLevel.NONE)
    @NotEmpty(message = "Lista de items não pode ser vazia!")
    private List<OrderItem> orderItems = new ArrayList<>();

    public OrderDTO(Order entity) {
        id = entity.getId();
        totalValue = entity.getTotalValue();
        customerCpf = entity.getCustomer().getCpf();
        status = entity.getStatus();
        date = entity.getDate();
    }
}

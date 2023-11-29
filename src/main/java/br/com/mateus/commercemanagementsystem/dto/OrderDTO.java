package br.com.mateus.commercemanagementsystem.dto;

import br.com.mateus.commercemanagementsystem.model.OrderItem;
import br.com.mateus.commercemanagementsystem.model.enums.OrderStatus;
import br.com.mateus.commercemanagementsystem.model.enums.PaymentType;
import jakarta.validation.constraints.DecimalMin;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    private PaymentType paymentType;
    private OrderStatus status;
    private LocalDateTime date;

    @Setter(AccessLevel.NONE)
    private List<OrderItem> orderItems;

    public OrderDTO(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}

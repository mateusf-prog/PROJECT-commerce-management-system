package br.com.mateus.commercemanagementsystem.dto;

import br.com.mateus.commercemanagementsystem.model.OrderItem;
import br.com.mateus.commercemanagementsystem.model.enums.OrderStatus;
import br.com.mateus.commercemanagementsystem.model.enums.PaymentType;
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

    private OrderStatus status;
    private String customer;
    private String cpf;
    private Long orderId;
    private Instant date;
    private List<OrderItem> listItems;
    private String paymentId;
    private PaymentType paymentType;
    private BigDecimal value;
    private String paymentCode;
    private String linkPdf;
}

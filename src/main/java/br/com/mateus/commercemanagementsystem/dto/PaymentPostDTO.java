package br.com.mateus.commercemanagementsystem.dto;

import br.com.mateus.commercemanagementsystem.model.enums.PaymentType;
import lombok.Data;

@Data
public class PaymentPostDTO {

    private Long orderId;
    private PaymentType paymentType;
}

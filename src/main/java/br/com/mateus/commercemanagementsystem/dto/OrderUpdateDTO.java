package br.com.mateus.commercemanagementsystem.dto;

import br.com.mateus.commercemanagementsystem.model.enums.PaymentType;
import lombok.Data;

@Data
public class OrderUpdateDTO {

    private Long id;
    private PaymentType paymentType;
}

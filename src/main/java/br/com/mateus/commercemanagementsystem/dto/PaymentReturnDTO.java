package br.com.mateus.commercemanagementsystem.dto;

import br.com.mateus.commercemanagementsystem.model.Payment;
import lombok.Data;

import java.time.Instant;

@Data
public class PaymentReturnDTO {

    private Long id;
    private String customerCpf;
    private Instant moment;
    private String status;
    private Double value;
    private String paymentType;
    private String description;
    private String paymentLink;

    public PaymentReturnDTO() {
    }

    public PaymentReturnDTO(Payment entity) {
        this.id = entity.getId();
        this.customerCpf = entity.getOrder().getCustomer().getCpf();
        this.moment = entity.getMoment();
        this.status = entity.getStatus().toString();
        this.value = entity.getValue().doubleValue();
        this.paymentType = entity.getPaymentType().toString();
        this.description = entity.getDescription();
        this.paymentLink = entity.getLinkPagamento();
    }
}

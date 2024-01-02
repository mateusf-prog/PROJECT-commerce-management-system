package br.com.mateus.commercemanagementsystem.integration.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class BillingResponse extends BillingRequest{
    
    // link boleto 
    private String bankSlipUrl;

    private String status;
    private String id;
    private String nossoNumero;
    private boolean deleted;
    private Double netValue;
    private String billingType;

    //data de pagamento
    private LocalDate clientPaymentDate;
    //data de compensacao
    private LocalDate paymentDate;
}

package br.com.mateus.commercemanagementsystem.model.model_asaas_integration;

import java.time.LocalDate;

import lombok.Data;

@Data
public class BillingRequest {
    
    private String customer;
    private String billingType;
    private Double value;
    private LocalDate dueDate;

    // id order
    private String description;
}
 
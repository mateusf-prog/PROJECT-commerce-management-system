package br.com.mateus.commercemanagementsystem.integration.model;

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
 
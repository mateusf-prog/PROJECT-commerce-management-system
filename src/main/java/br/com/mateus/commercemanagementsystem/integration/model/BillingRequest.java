package br.com.mateus.commercemanagementsystem.integration.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class BoletoRequest {
    
    private String customer;
    private String billingType = "BOLETO";
    private Double value;
    private LocalDate dueDate;
    private String description;
}
 
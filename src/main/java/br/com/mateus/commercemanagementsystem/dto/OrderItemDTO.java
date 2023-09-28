package br.com.mateus.commercemanagementsystem.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDTO {

    private String productName;
    private int quantity;
    private BigDecimal price;
}

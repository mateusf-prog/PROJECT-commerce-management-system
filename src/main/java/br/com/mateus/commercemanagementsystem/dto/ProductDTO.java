package br.com.mateus.commercemanagementsystem.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {

    private Long id;
    private String name;
    private BigDecimal price;
    private int quantity;
    private String category;
}

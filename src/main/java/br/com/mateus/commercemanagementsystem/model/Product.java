package br.com.mateus.commercemanagementsystem.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import br.com.mateus.commercemanagementsystem.model.enums.Categories;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Categories category;
    
    public Product() {
    }

    public Product(String name, BigDecimal price, int quantity, Categories category) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }
}

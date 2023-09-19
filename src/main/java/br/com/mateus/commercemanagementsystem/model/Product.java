package br.com.mateus.commercemanagementsystem.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import br.com.mateus.commercemanagementsystem.model.enums.Categories;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code", unique = true)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "category", nullable = false)
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

package br.com.mateus.commercemanagementsystem.model;

import java.math.BigDecimal;
import java.util.Objects;

import br.com.mateus.commercemanagementsystem.model.enums.Categories;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class Product {

    @Id
    @Column(name = "code", unique = true)
    private Long code;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private Categories category;
    
    public Product() {
    }

    public Product(Long code, String name, BigDecimal price, int quantity, Categories category) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(code, product.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return "-- Product --"
            + "\nCode: " + code
            + "\nName: " + name
            + "\nPrice: " + price
            + "\nCategory: " + category;
    }
}

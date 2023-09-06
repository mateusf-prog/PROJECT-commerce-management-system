package br.com.mateus.commercemanagementsystem.model;

import java.math.BigDecimal;

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
    @Column(name = "product_code", unique = true)
    private String code;

    @Column(name = "product_name")
    private String name;

    @Column(name = "product_price")
    private BigDecimal price;

    @Column(name = "product_quantity")
    private int quantity;

    @Column(name = "product_category")
    @Enumerated(EnumType.STRING)
    private Categories category;
    
    public Product() {
    }

    public Product(String code, String name, BigDecimal price, int quantity, Categories category) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Product other = (Product) obj;
        if (code == null) {
            if (other.code != null)
                return false;
        } else if (!code.equals(other.code))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        return result;
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

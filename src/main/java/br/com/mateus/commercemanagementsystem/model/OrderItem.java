package br.com.mateus.commercemanagementsystem.model;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    // define relationships

    @ManyToOne()
    private Order order;

    public OrderItem() {
    }

    public OrderItem(String productName, int quantity, BigDecimal price, Order order) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.order = order;
    }

    public String toString() {
        return "Name: " + productName + ", quantity: " + quantity + ", price: " + price + ", order: " + order.getId();
    }
}

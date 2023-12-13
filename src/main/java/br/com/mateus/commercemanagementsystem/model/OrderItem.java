package br.com.mateus.commercemanagementsystem.model;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Entity
@Table(name = "tb_order_item")
public class OrderItem {

    @EmbeddedId
    private OrderItemPK id = new OrderItemPK();

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(nullable = false)
    private int quantity;

    public OrderItem() {
    }

    public OrderItem(Order order, Product product, String productName, int quantity, BigDecimal price) {
        id.setOrder(order);
        id.setProduct(product);
        this.productName = productName;
        this.quantity = quantity;
    }
}

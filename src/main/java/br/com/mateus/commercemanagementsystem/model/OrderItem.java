package br.com.mateus.commercemanagementsystem.model;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_order_item")
public class OrderItem {

    @EmbeddedId
    @Getter
    private OrderItemPK id = new OrderItemPK();

    @Getter
    @Setter
    @Column(nullable = false)
    private int quantity;

    public OrderItem() {
    }

    public OrderItem(Order order, Product product, int quantity, BigDecimal price) {
        id.setProduct(product);
        id.setOrder(order);
        this.quantity = quantity;
    }

    public Product getProduct() {
        return id.getProduct();
    }

    public void setProduct(Product product) {
        id.setProduct(product);
    }
}

package br.com.mateus.commercemanagementsystem.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @Column(name = "order_items_id")
    @Setter(AccessLevel.NONE)
    private int id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_quantity")
    private int quantity;

    @Column(name = "product_price")
    private BigDecimal price;
    
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderItem() {
    }

    public OrderItem(String productName, int quantity, BigDecimal price, Order order) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.order = order;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OrderItem other = (OrderItem) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    


}

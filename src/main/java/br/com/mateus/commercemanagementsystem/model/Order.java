package br.com.mateus.commercemanagementsystem.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "order_totalValue", nullable = false)
    private BigDecimal totalValue;

    // define relationships

    @ManyToOne
    @Setter(AccessLevel.NONE)
    private Client client;

    @OneToOne
    private Payment payment;

    @OneToMany(mappedBy = "order")
    @Setter(AccessLevel.NONE)
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order() {
    }

    public Order(Long id, BigDecimal totalValue, Payment payment, Client client, List<OrderItem> orderItems) {
        this.id = id;
        this.totalValue = totalValue;
        this.payment = payment;
        this.client = client;
        this.orderItems = orderItems;
    }
}

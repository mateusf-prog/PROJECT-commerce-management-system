package br.com.mateus.commercemanagementsystem.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.mateus.commercemanagementsystem.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "total_value", nullable = false)
    private BigDecimal totalValue;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private OrderStatus status;

    // define relationships

    @ManyToOne
    private Client client;

    @OneToOne(cascade = CascadeType.ALL)
    private Payment payment;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Setter(AccessLevel.NONE)
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order() {
    }

    public Order(BigDecimal totalValue, Payment payment, Client client,
                 List<OrderItem> orderItems, LocalDateTime date, OrderStatus status) {
        this.totalValue = totalValue;
        this.payment = payment;
        this.client = client;
        this.orderItems = orderItems;
        this.date = date;
        this.status = status;
    }

    public Order(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}

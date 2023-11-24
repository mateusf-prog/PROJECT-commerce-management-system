package br.com.mateus.commercemanagementsystem.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "total_value", nullable = false, precision = 7, scale = 2)
    private BigDecimal totalValue;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private OrderStatus status;

    // define relationships

    @ManyToOne
    private Customer customer;

    @OneToOne(cascade = CascadeType.ALL)
    private Payment payment;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Setter(AccessLevel.NONE)
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order() {
    }

    public Order(BigDecimal totalValue, Payment payment, Customer customer,
                 List<OrderItem> orderItems, LocalDateTime date, OrderStatus status) {
        this.totalValue = totalValue;
        this.payment = payment;
        this.customer = customer;
        this.orderItems = orderItems;
        this.date = date;
        this.status = status;
    }

    public Order(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}

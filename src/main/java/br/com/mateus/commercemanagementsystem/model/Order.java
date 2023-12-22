package br.com.mateus.commercemanagementsystem.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.mateus.commercemanagementsystem.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_order")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "total_value", nullable = false, precision = 7, scale = 2)
    private BigDecimal totalValue;

    @Column(nullable = false)
    private Instant date;

    @Column(nullable = false)
    private OrderStatus status;

    // define relationships

    @OneToMany(mappedBy = "id.order" )
    @Setter(AccessLevel.NONE)
    private Set<OrderItem> items = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne
    private Payment payment;

    public Order() {
    }

    public Order(BigDecimal totalValue, Payment payment, Customer customer, Instant date, OrderStatus status) {
        this.totalValue = totalValue;
        this.payment = payment;
        this.customer = customer;
        this.date = date;
        this.status = status;
    }

    public List<Product> getProducts() {
        return items.stream().map(x -> x.getProduct()).toList();
    }
}

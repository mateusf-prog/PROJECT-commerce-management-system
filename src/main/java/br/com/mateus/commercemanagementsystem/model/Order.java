package br.com.mateus.commercemanagementsystem.model;

import br.com.mateus.commercemanagementsystem.dto.OrderItemDTO;
import br.com.mateus.commercemanagementsystem.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_order")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "total_value", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalValue;

    @Column(nullable = false)
    private Instant date;

    @Column(nullable = false)
    private OrderStatus status;

    // define relationships

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Setter(AccessLevel.NONE)
    private List<OrderItem> items = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne
    private Payment payment;

    public Order() {
    }

    public Order(List<OrderItem> items) {
        this.items = items;
    }

    public Order(BigDecimal totalValue, Payment payment, Customer customer, Instant date, OrderStatus status) {
        this.totalValue = totalValue;
        this.payment = payment;
        this.customer = customer;
        this.date = date;
        this.status = status;
    }

    public List<Product> getProducts() {
        return items.stream().map(OrderItem::getProduct).toList();
    }
}

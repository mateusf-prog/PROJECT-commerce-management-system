package br.com.mateus.commercemanagementsystem.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_totalValue", nullable = false)
    private BigDecimal totalValue;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    // define relationships

    @ManyToOne
    @Setter(AccessLevel.NONE)
    private Client client;

    @OneToOne
    private Payment payment;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    @Setter(AccessLevel.NONE)
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order() {
    }

    public Order(BigDecimal totalValue, Payment payment, Client client,
                 List<OrderItem> orderItems, LocalDateTime date) {
        this.totalValue = totalValue;
        this.payment = payment;
        this.client = client;
        this.orderItems = orderItems;
        this.date = date;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order [")
                .append("id: ").append(id)
                .append(", totalValue: ").append(totalValue)
                .append(", date=").append(date)
                .append(", client: ").append(client != null ? client.getName() : "N/A")
                .append(", payment: ").append(payment != null ? payment.getId() : "N/A")
                .append("]\n");

        sb.append("Order Items:\n");
        for (OrderItem item : orderItems) {
            sb.append(item).append("\n");
        }

        return sb.toString();
    }

}

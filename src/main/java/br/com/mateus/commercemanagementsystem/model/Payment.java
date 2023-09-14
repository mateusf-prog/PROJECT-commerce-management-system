package br.com.mateus.commercemanagementsystem.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import br.com.mateus.commercemanagementsystem.model.enums.PaymentStatus;
import br.com.mateus.commercemanagementsystem.model.enums.PaymentType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "payments")
@Data
public class Payment {

    @Id
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Column(name = "value", nullable = false)
    private BigDecimal value;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    // define relationships

    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;

    @OneToOne(mappedBy = "payment")
    private Order order;
    
    public Payment() {
    }

    public Payment(PaymentType paymentType, Long id, BigDecimal value, LocalDateTime date,
                   PaymentStatus status, Client client, Order order) {
        this.paymentType = paymentType;
        this.id = id;
        this.value = value;
        this.date = date;
        this.status = status;
        this.client = client;
        this.order = order;
    }
}

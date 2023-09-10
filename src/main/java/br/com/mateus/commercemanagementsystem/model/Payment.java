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
    @Column(name = "code", unique = true)
    private Long code;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Column(name = "value")
    private BigDecimal value;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    // define relationships

    @ManyToOne
    @JoinColumn(name = "client_cpf")
    private Client client;

    @OneToOne(mappedBy = "payment",cascade = CascadeType.ALL)
    private Order order;
    
    public Payment() {
    }

    public Payment(PaymentType paymentType, Long code, BigDecimal value, LocalDateTime date,
                   PaymentStatus status, Client client, Order order) {
        this.paymentType = paymentType;
        this.code = code;
        this.value = value;
        this.date = date;
        this.status = status;
        this.client = client;
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(code, payment.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return "-- Payment --"
            + "\nCode: " + code
            + "\nClient: " + client.getName()
            + "\nDate: " + date
            + "\nValue: " + value
            + "\nStatus: " + status;
    }
}

package br.com.mateus.commercemanagementsystem.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.ManyToAny;

import br.com.mateus.commercemanagementsystem.model.enums.PaymentStatus;
import br.com.mateus.commercemanagementsystem.model.enums.PaymentType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Entity
@Table(name = "payments")
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "payment_id")
    private int id;

    @Column(name = "payment_code")
    private String code;

    @Column(name = "payment_type")
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Column(name = "payment_value")
    private BigDecimal value;

    @Column(name = "payment_date")
    private LocalDateTime date;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(name = "payment_client")
    @ManyToAny
    @JoinColumn(name = "client_id")
    private Client client;
    
    public Payment() {
    }

    public Payment(PaymentType paymentType, BigDecimal value, LocalDateTime date, PaymentStatus status, Client client) {
        this.paymentType = paymentType;
        this.value = value;
        this.date = date;
        this.status = status;
        this.client = client;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Payment other = (Payment) obj;
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

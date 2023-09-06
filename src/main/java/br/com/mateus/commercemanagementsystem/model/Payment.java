package br.com.mateus.commercemanagementsystem.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.mateus.commercemanagementsystem.model.enums.PaymentStatus;
import br.com.mateus.commercemanagementsystem.model.enums.PaymentType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "payments")
@Data
public class Payment {

    @Id
    @Column(name = "payment_code", unique = true)
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

    // define relationships

    @ManyToOne
    @JoinColumn(name = "client_id") 
    private Client client;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;
    
    public Payment() {
    }

    public Payment(PaymentType paymentType, String code, BigDecimal value, 
                LocalDateTime date,PaymentStatus status, Client client, Order order) {
        this.paymentType = paymentType;
        this.code = code;
        this.value = value;
        this.date = date;
        this.status = status;
        this.client = client;
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
        Payment other = (Payment) obj;
        if (code == null) {
            if (other.code != null)
                return false;
        } else if (!code.equals(other.code))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((code == null) ? 0 : code.hashCode());
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

package br.com.mateus.commercemanagementsystem.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.mateus.commercemanagementsystem.model.enums.PaymentStatus;
import br.com.mateus.commercemanagementsystem.model.enums.PaymentType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Entity
@Table(name = "tb_payment")
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "payment_type" , nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Column(nullable = false)
    private BigDecimal value;

    private LocalDateTime date;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(name = "id_api_external")
    private String idApiExternal;

    // define relationships

    @OneToOne
    @MapsId
    private Order order;
    
    public Payment() {
    }

    public Payment(PaymentType paymentType, BigDecimal value, LocalDateTime date,
                   PaymentStatus status, Order order) {
        this.paymentType = paymentType;
        this.value = value;
        this.date = date;
        this.status = status;
        this.order = order;
    }
}

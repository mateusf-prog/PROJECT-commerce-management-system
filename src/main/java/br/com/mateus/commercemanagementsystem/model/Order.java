package br.com.mateus.commercemanagementsystem.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @Column(name = "order_code", unique = true)
    private String code;

    @Column(name = "order_totalValue")
    private BigDecimal totalValue;

    // define relationships

    @ManyToOne
    @JoinColumn(name = "client_id")
    @Setter(AccessLevel.NONE)
    private Client client;

    @OneToOne(mappedBy = "order")
    private Payment payment;

    @OneToMany
    @JoinColumn(name = "order_id")
    private List<OrderItem> orderItems;

    public Order() {
    }

    public Order(String code, BigDecimal totalValue, Payment payment, Client client, List<OrderItem> orderItems) {
        this.code = code;
        this.totalValue = totalValue;
        this.payment = payment;
        this.client = client;
        this.orderItems = new ArrayList<>();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Order other = (Order) obj;
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
        StringBuilder sb = new StringBuilder();
        sb.append("-- Order --")
        .append("\nClient: ").append(client.getName())
        .append("\nTotal value: ").append(totalValue);
    
        return sb.toString();
    }
}

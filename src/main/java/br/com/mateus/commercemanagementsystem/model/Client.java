package br.com.mateus.commercemanagementsystem.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Entity
@Table(name = "clients")
@Data
public class Client {
    
    @Column(name = "name", nullable = false)
    private String name;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @Id
    @Column(name = "cpf", unique = true, nullable = false)
    private String cpf;

    @Column(name = "phoneNumber", nullable = false)
    private String phoneNumber;

    @Column(name = "address", nullable = false)
    private String address;

    // define relationships

    @OneToMany(mappedBy = "client", fetch=FetchType.EAGER)
    @Setter(AccessLevel.NONE)
    private List<Order> orders = new ArrayList<>();

    public Client() {
    }

    public Client(String name, LocalDate birthdate, String cpf, String phoneNumber, String address) {
        this.name = name;
        this.birthdate = birthdate;
        this.cpf = cpf;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Client [")
                .append("cpf=").append(cpf)
                .append(", name=").append(name)
                .append(", birthdate=").append(birthdate)
                .append(", phoneNumber=").append(phoneNumber)
                .append(", address=").append(address)
                .append("]\n");

        sb.append("Orders:\n");
        for (Order order : orders) {
            sb.append(order).append("\n");
        }
        return sb.toString();
    }
}

package br.com.mateus.commercemanagementsystem.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Entity
@Table(name = "clients")
@Data
public class Client {
    
    @Column(name = "name")
    private String name;

    @Column(name = "birthdate")
    private LocalDate birthdate;

    @Id
    @Column(name = "cpf", unique = true)
    @Setter(AccessLevel.NONE)
    private String cpf;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    // define relationships
    
    @OneToMany(mappedBy = "client")
    @Setter(AccessLevel.NONE)
    private List<Payment> payments = new ArrayList<>();

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
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
}

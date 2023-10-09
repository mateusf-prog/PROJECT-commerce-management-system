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
    
    @Column(nullable = false)
    private String name;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(nullable = false)
    private LocalDate birthdate;

    @Id
    @Column(unique = true, nullable = false)
    private String cpf;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    private String email;

    @Column(nullable = false)
    private String address;

    // define relationships

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @Setter(AccessLevel.NONE)
    private List<Order> orders = new ArrayList<>();

    public Client() {
    }

    public Client(String name, LocalDate birthdate, String cpf, String phoneNumber, String address, String email) {
        this.name = name;
        this.birthdate = birthdate;
        this.cpf = cpf;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
    }
}

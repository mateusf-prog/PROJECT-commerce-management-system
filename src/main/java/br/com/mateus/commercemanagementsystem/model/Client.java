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

    @Column(name = "adress")
    private String adress;

    // define relationships
    
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @Setter(AccessLevel.NONE)
    private List<Payment> payments = new ArrayList<>();

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @Setter(AccessLevel.NONE)
    private List<Order> orders = new ArrayList<>();

    public Client() {
    }

    public Client(String name, LocalDate birthdate, String cpf, String phoneNumber, String adress) {
        this.name = name;
        this.birthdate = birthdate;
        this.cpf = cpf;
        this.phoneNumber = phoneNumber;
        this.adress = adress;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Client other = (Client) obj;
        if (cpf == null) {
            if (other.cpf != null)
                return false;
        } else if (!cpf.equals(other.cpf))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "-- Client --"
            + "\nName: " + name
            + "\nBirthdate: " + birthdate
            + "\nCPF: " + cpf
            + "\nPhone number: " + phoneNumber
            + "\nAdress: " + adress; 
    }
}

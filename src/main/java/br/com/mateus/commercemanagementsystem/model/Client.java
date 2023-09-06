package br.com.mateus.commercemanagementsystem.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Entity
@Table(name = "clients")
@Data
public class Client {
    
    @Column(name = "client_name")
    private String name;

    @Column(name = "client_birthdate")
    private LocalDate birthdate;

    @Id
    @Column(name = "client_cpf", unique = true)
    private String cpf;

    @Column(name = "client_phoneNumber")
    private String phoneNumber;

    @Column(name = "client_adress")
    private String adress;

    // define relationships
    
    @OneToMany(mappedBy = "client") 
    @Setter(AccessLevel.NONE)
    private List<Payment> payments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "commerce_id")
    private Commerce commerce;

    @OneToMany(mappedBy = "client")
    @Setter(AccessLevel.NONE)
    private List<Order> orders;

    public Client() {
    }

    public Client(String name, LocalDate birthdate, String cpf, String phoneNumber, String adress, Commerce commerce) {
        this.name = name;
        this.birthdate = birthdate;
        this.cpf = cpf;
        this.phoneNumber = phoneNumber;
        this.adress = adress;
        this.commerce = commerce;
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

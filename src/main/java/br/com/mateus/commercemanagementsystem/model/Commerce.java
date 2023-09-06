package br.com.mateus.commercemanagementsystem.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Entity
@Table(name = "commerces")
@Data
public class Commerce {

    @Column(name = "commerce_name")
    private String name;

    @Id
    @Column(name = "commerce_cnpj", unique = true)
    private String cnpj;

    @Column(name = "commerce_phone")
    private String phoneNumber;

    @Column(name = "commerce_adress")
    private String adress;

    // define relationships
    
    @OneToMany(mappedBy = "commerce")
    @Setter(AccessLevel.NONE)
    private List<Client> clients;

    @OneToMany(mappedBy = "commerce")
    @Setter(AccessLevel.NONE)
    private List<Order> orders;

    @OneToMany(mappedBy = "commerce")
    @Setter(AccessLevel.NONE)
    private List<Product> products;

    public Commerce() {
    }

    public Commerce(String name, String cnpj, String phoneNumber, String adress) {
        this.name = name;
        this.cnpj = cnpj;
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
        Commerce other = (Commerce) obj;
        if (cnpj == null) {
            if (other.cnpj != null)
                return false;
        } else if (!cnpj.equals(other.cnpj))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "-- Commerce --"
            + "\nName: " + name
            + "\nCNPJ: " + cnpj
            + "\nPhone number: " + phoneNumber
            + "\nAdress: " + adress; 
    }
}

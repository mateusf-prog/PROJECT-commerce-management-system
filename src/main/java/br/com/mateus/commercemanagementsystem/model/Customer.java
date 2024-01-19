package br.com.mateus.commercemanagementsystem.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.mateus.commercemanagementsystem.dto.CustomerDTO;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Entity
@Table(name = "tb_customer")
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "cpf")
public class Customer {
    
    @Column(nullable = false)
    @NotBlank(message = "Nome não pode ficar em branco!")
    @Size(min = 3, max = 50, message = "Nome deve conter entre 3 e 50 caracteres!")
    private String name;

    @Column(name = "id_api_external")
    private String idApiExternal;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Past(message = "Data de nascimento inválida")
    @Column(nullable = false)
    private LocalDate birthdate;

    @Id
    @Column(unique = true, nullable = false)
    @CPF(message = "CPF inválido!")
    private String cpf;

    @Column(name = "phone_number", nullable = false)
    @NotBlank(message = "Número de telefone não pode ficar em branco!")
    private String phoneNumber;

    @Column(nullable = false)
    @Email(message = "Email inválido!")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "Endereço não pode ficar em branco!")
    private String address;

    // define relationships

    @OneToMany(mappedBy = "customer", fetch=FetchType.EAGER)
    @Setter(AccessLevel.NONE)
    private List<Order> orders = new ArrayList<>();

    public Customer() {
    }

    public Customer(String name, LocalDate birthdate, String cpf, String phoneNumber, String address, String email) {
        this.name = name;
        this.birthdate = birthdate;
        this.cpf = cpf;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
    }
}

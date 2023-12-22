package br.com.mateus.commercemanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity(name = "tb_category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotBlank(message = "Nome da categoria não pode ficar em branco!")
    @Size(min = 3, max = 30, message = "Nome deve conter entre 3 e 30 caracteres!")
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.MERGE)
    @Setter(AccessLevel.NONE)
    private Set<Product> products = new HashSet<>();
    // todo: verificar a lógica de adição da lista de  produtos na categoria

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }
}
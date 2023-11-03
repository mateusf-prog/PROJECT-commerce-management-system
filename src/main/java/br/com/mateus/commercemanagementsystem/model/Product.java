package br.com.mateus.commercemanagementsystem.model;

import java.math.BigDecimal;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Nome do produto não pode ficar em branco!")
    @Size(min = 3, max = 50, message = "Nome deve conter entre 3 e 50 caracteres!")
    private String name;

    @Column(nullable = false)
    @DecimalMin(value = "0.0", inclusive = false, message = "Preço deve ser maior que zero!")
    private BigDecimal price;

    @Column(nullable = false)
    @Min(value = 1, message = "A quantidade deve ser maior ou igual a zero!")
    private int quantity;

    @OneToOne
    private Category category;
    
    public Product() {
    }

    public Product(String name, BigDecimal price, int quantity, Category category) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }
}

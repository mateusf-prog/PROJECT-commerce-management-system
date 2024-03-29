package br.com.mateus.commercemanagementsystem.model;

import java.math.BigDecimal;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Table(name = "tb_product")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Nome do produto não pode ficar em branco!")
    private String name;

    @Column(nullable = false, precision = 7, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "Preço deve ser maior que zero!")
    private BigDecimal price;

    @Column(nullable = false)
    @Min(value = 0, message = "A quantidade deve ser maior ou igual a zero!")
    private int quantity;

    @ManyToOne()
    @NotNull(message = "Categoria não pode ser nula")
    private Category category;
    
    public Product() {
    }

    public Product(String name, BigDecimal price, int quantity, Category category) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
}

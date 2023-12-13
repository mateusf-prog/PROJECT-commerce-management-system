package br.com.mateus.commercemanagementsystem.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Entity
@Table(name = "tb_product")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Nome do produto não pode ficar em branco!")
    @Size(min = 3, max = 50, message = "Nome deve conter entre 3 e 50 caracteres!")
    private String name;

    @Column(nullable = false, precision = 7, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "Preço deve ser maior que zero!")
    private BigDecimal price;

    @Column(nullable = false)
    @Min(value = 1, message = "A quantidade deve ser maior ou igual a zero!")
    private int quantity;

    @OneToMany(mappedBy = "id.product")
    @Setter(AccessLevel.NONE)
    private Set<OrderItem> items = new HashSet<>();

    @Setter(AccessLevel.NONE)
    @ManyToMany
    @JoinTable(name = "tb_product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();
    
    public Product() {
    }

    public Product(String name, BigDecimal price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}

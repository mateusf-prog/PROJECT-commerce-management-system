package br.com.mateus.commercemanagementsystem.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderItemDTO {

    @NotBlank(message = "ID do produto não pode ficar em branco!")
    private Long productId;
    @NotBlank(message = "Nome do produto não pode ficar em branco!")
    private String productName;
    @Min(value = 1, message = "Quantidade deve ser maior que zero!")
    private int quantity;
}

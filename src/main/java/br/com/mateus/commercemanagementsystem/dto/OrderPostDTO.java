package br.com.mateus.commercemanagementsystem.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderPostDTO {
    
    @CPF
    private String customerCpf;
    @NotEmpty(message = "Lista de items não pode ser vazia")
    private List<OrderItemDTO> items;
}

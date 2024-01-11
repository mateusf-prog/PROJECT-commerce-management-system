package br.com.mateus.commercemanagementsystem.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import br.com.mateus.commercemanagementsystem.model.enums.PaymentType;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderPostDTO {
    
    @CPF
    private String customerCpf;
    @NotNull(message = "Tipo de pagamento é obrigatório")
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
    @NotEmpty(message = "Lista de items não pode ser vazia")
    private List<OrderItemDTO> items;
}

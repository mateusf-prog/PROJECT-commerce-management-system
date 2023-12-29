package br.com.mateus.commercemanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCreatedDTO {

    private String name;
    private String cpfCnpj;
    private String email;
    private String mobilePhone;
}

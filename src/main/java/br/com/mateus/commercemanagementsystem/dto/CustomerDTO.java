package br.com.mateus.commercemanagementsystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerDTO {

    private String id;
    private String name;
    private String cpfCnpj;
    private String email;
    private String mobilePhone;
}

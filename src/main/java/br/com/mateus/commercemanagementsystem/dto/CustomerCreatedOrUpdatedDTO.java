package br.com.mateus.commercemanagementsystem.dto;

import br.com.mateus.commercemanagementsystem.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCreatedOrUpdatedDTO {

    private String name;
    private String cpf;
    private String email;
    private String phoneNumber;

    public CustomerCreatedOrUpdatedDTO(Customer entity) {
        name = entity.getName();
        cpf = entity.getCpf();
        email = entity.getEmail();
        phoneNumber = entity.getPhoneNumber();
    }
}

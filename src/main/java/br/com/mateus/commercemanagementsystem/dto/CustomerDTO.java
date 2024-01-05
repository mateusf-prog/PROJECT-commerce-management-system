package br.com.mateus.commercemanagementsystem.dto;

import br.com.mateus.commercemanagementsystem.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    private String idApiExternal;
    private String name;
    private String cpfCnpj;
    private String email;
    private String phoneNumber;

    public CustomerDTO(Customer entity) {
        idApiExternal = entity.getIdApiExternal();
        name = entity.getName();
        cpfCnpj = entity.getCpf();
        email = entity.getEmail();
        phoneNumber = entity.getPhoneNumber();
    }
}

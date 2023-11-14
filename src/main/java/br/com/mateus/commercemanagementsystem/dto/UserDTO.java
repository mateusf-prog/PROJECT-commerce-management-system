package br.com.mateus.commercemanagementsystem.dto;

import br.com.mateus.commercemanagementsystem.model.enums.UserRole;
import lombok.Data;

@Data
public class UserDTO {

    private String name;
    private UserRole role;
}

package br.com.mateus.commercemanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CategoryDTO {

    private Long id;

    @NotBlank(message = "Nome da categoria não pode ficar em branco!")
    private String name;

    public CategoryDTO() {
    }

    public CategoryDTO(String name) {
        this.name = name;
    }
}

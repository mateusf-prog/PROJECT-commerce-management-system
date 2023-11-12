package br.com.mateus.commercemanagementsystem.dto;

import br.com.mateus.commercemanagementsystem.model.enums.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}

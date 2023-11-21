package br.com.mateus.commercemanagementsystem.model;

import br.com.mateus.commercemanagementsystem.model.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<UserRole> role;
}

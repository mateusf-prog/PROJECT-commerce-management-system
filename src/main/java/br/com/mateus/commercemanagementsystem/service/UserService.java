package br.com.mateus.commercemanagementsystem.service;

import br.com.mateus.commercemanagementsystem.dto.UserDTO;
import br.com.mateus.commercemanagementsystem.exceptions.EntityNotFoundException;
import br.com.mateus.commercemanagementsystem.model.User;
import br.com.mateus.commercemanagementsystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> findAll() {

        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new EntityNotFoundException("Nenhum usuário encontrado");
        }

        List<UserDTO> usersDTO = new ArrayList<>();
        for (User user : users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setName(user.getLogin());
            userDTO.setRole(user.getRole());
            usersDTO.add(userDTO);
        }

        return usersDTO;
    }
}

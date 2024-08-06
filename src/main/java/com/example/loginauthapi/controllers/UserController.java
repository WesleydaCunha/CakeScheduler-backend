package com.example.loginauthapi.controllers;

import com.example.loginauthapi.domain.user.User;
import com.example.loginauthapi.dto.ResponseClientUserListDTO;
import com.example.loginauthapi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    @GetMapping
    public ResponseEntity<String> getUser(){
        return ResponseEntity.ok("sucesso!");
    }

    private final UserRepository repository;

    @GetMapping("/get")
    public ResponseEntity<List<ResponseClientUserListDTO>> getAllUsers() {
        List<User> userList = repository.findAll();

        // Transformar a lista de usuários em lista de DTOs
        List<ResponseClientUserListDTO> userDTOs = userList.stream()
                .map(user -> new ResponseClientUserListDTO(user.getId(), user.getName(), user.getPhone(), user.getEmail()))
                .toList();

        return ResponseEntity.ok(userDTOs);
    }
}


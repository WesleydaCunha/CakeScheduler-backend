package com.example.cakescheduler.controllers;

import com.example.cakescheduler.domain.user.UserClient;
import com.example.cakescheduler.domain.user.UserEmployee;
import com.example.cakescheduler.dto.RegisterRequestDTO;
import com.example.cakescheduler.dto.ResponseClientUserListDTO;
import com.example.cakescheduler.infra.security.TokenService;
import com.example.cakescheduler.repositories.UserClientRepository;
import com.example.cakescheduler.repositories.UserEmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserClientRepository userClientRepository;
    private final UserEmployeeRepository userEmployeeRepository;
    private final TokenService tokenService;

    @GetMapping
    public ResponseEntity<String> getUser() {
        return ResponseEntity.ok("sucesso!");
    }

    @GetMapping("/get")
    public ResponseEntity<List<ResponseClientUserListDTO>> getAllUsers() {
        List<UserClient> userClientsList = userClientRepository.findAll();
        List<ResponseClientUserListDTO> userDTOs = userClientsList.stream()
                .map(user -> new ResponseClientUserListDTO(user.getId(), user.getName(), user.getPhone(), user.getEmail()))
                .toList();

        return ResponseEntity.ok(userDTOs);
    }

    @PatchMapping("/profile/{id}")
    public ResponseEntity<String> updateComplement(@PathVariable String id, @RequestBody RegisterRequestDTO body) {
        Optional<UserEmployee> existingUser = userEmployeeRepository.findById(id);

        if (existingUser.isPresent()) {
            UserEmployee userEmployee = existingUser.get();
            userEmployee.setName(body.name());
            userEmployee.setPhone(body.phone());
            userEmployee.setPassword(passwordEncoder.encode(body.password()));
            userEmployeeRepository.save(userEmployee);
            return ResponseEntity.ok("Complemento atualizado com sucesso!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<ResponseClientUserListDTO> getUserProfile(@RequestHeader("Authorization") String token) {
        // Remove o prefixo "Bearer " do token
        String jwtToken = token.substring(7);

        // Validar o token e extrair o email do usuário
        String userEmail = tokenService.validateToken(jwtToken);
        if (userEmail == null) {
            return ResponseEntity.status(401).body(null); // Retorna 401 se o token for inválido
        }

        // Busca o usuário pelo email
        Optional<UserEmployee> userOptional = userEmployeeRepository.findByEmail(userEmail);

        if (userOptional.isPresent()) {
            UserEmployee user = userOptional.get();
            ResponseClientUserListDTO userDTO = new ResponseClientUserListDTO(
                    user.getId(),
                    user.getName(),
                    user.getPhone(),
                    user.getEmail()
            );
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/profile/client/{id}")
    public ResponseEntity<String> updateClient(@PathVariable String id, @RequestBody RegisterRequestDTO body) {
        Optional<UserClient> existingUser = userClientRepository.findById(id);

        if (existingUser.isPresent()) {
            UserClient userClient = existingUser.get();
            userClient.setName(body.name());
            userClient.setPhone(body.phone());
            userClient.setPassword(passwordEncoder.encode(body.password()));
            userClientRepository.save(userClient);
            return ResponseEntity.ok("Complemento atualizado com sucesso!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/profile/client")
    public ResponseEntity<ResponseClientUserListDTO> getClientUserProfile(@RequestHeader("Authorization") String token) {
        // Remove o prefixo "Bearer " do token
        String jwtToken = token.substring(7);

        // Validar o token e extrair o email do usuário
        String userEmail = tokenService.validateToken(jwtToken);
        if (userEmail == null) {
            return ResponseEntity.status(401).body(null); // Retorna 401 se o token for inválido
        }

        // Busca o usuário pelo email
        Optional<UserClient> userOptional = userClientRepository.findByEmail(userEmail);

        if (userOptional.isPresent()) {
            UserClient user = userOptional.get();
            ResponseClientUserListDTO userDTO = new ResponseClientUserListDTO(
                    user.getId(),
                    user.getName(),
                    user.getPhone(),
                    user.getEmail()
            );
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}


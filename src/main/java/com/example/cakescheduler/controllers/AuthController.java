package com.example.cakescheduler.controllers;

import com.example.cakescheduler.domain.user.UserClient;
import com.example.cakescheduler.domain.user.UserEmployee;
import com.example.cakescheduler.dto.LoginRequestDTO;
import com.example.cakescheduler.dto.RegisterRequestDTO;
import com.example.cakescheduler.dto.ResponseDTO;
import com.example.cakescheduler.infra.security.TokenService;
import com.example.cakescheduler.repositories.UserClientRepository;
import com.example.cakescheduler.repositories.UserEmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserEmployeeRepository repository;
    private final UserClientRepository userClientRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body) {
        UserEmployee userEmployee = this.repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found"));
        if (passwordEncoder.matches(body.password(), userEmployee.getPassword())) {
            String token = this.tokenService.generateToken(userEmployee);
            return ResponseEntity.ok(new ResponseDTO(userEmployee.getName(), token));
        }
        return ResponseEntity.badRequest().build();
    }


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body) {
        Optional<UserEmployee> user = this.repository.findByEmail(body.email());


        if (user.isEmpty()) {
            UserEmployee newUserEmployee = new UserEmployee();
            newUserEmployee.setPassword(passwordEncoder.encode(body.password()));
            newUserEmployee.setEmail(body.email());
            newUserEmployee.setName(body.name());
            newUserEmployee.setPhone(body.phone());

            this.repository.save(newUserEmployee);

            String token = this.tokenService.generateToken(newUserEmployee);
            return ResponseEntity.ok(new ResponseDTO(newUserEmployee.getName(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/login_client")
    public ResponseEntity login_client(@RequestBody LoginRequestDTO body) {
        UserClient userClient = this.userClientRepository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found"));
        if (passwordEncoder.matches(body.password(), userClient.getPassword())) {
            String token = this.tokenService.generateToken(userClient);
            return ResponseEntity.ok(new ResponseDTO(userClient.getName(), token));
        }
        return ResponseEntity.badRequest().build();
    }


    @PostMapping("/register_client")
    public ResponseEntity register_client(@RequestBody RegisterRequestDTO body) {
        Optional<UserClient> user = this.userClientRepository.findByEmail(body.email());

        if (user.isEmpty()) {
            UserClient newUserClient = new UserClient();
            newUserClient.setPassword(passwordEncoder.encode(body.password()));
            newUserClient.setEmail(body.email());
            newUserClient.setName(body.name());
            newUserClient.setPhone(body.phone());

            this.userClientRepository.save(newUserClient);

            String token = this.tokenService.generateToken(newUserClient);
            return ResponseEntity.ok(new ResponseDTO(newUserClient.getName(), token));
        }
        return ResponseEntity.badRequest().build();
    }


}

package com.example.cakescheduler.infra.security;

import com.example.cakescheduler.domain.user.UserEmployee;
import com.example.cakescheduler.repositories.UserEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
public class CreateUserEmployeeDefault {

    @Autowired
    private UserEmployeeRepository repository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner createDefaultUser() {
        return args -> {
            // Adiciona log para verificar se o método está sendo chamado
            System.out.println("Checking if master user exists...");
            Optional<UserEmployee> user = repository.findByEmail("wesleydacunhafrancisco@hotmail.com");
            if (user.isEmpty()) {
                UserEmployee newUserEmployee = new UserEmployee();
                newUserEmployee.setPassword(passwordEncoder.encode("123456"));
                newUserEmployee.setEmail("wesleydacunhafrancisco@hotmail.com");
                newUserEmployee.setName("Master Company");
                newUserEmployee.setPhone("999999999");

                repository.save(newUserEmployee);

                String token = tokenService.generateToken(newUserEmployee);
                System.out.println("Master user saved successfully");
            } else {
                System.out.println("Master user already exists.");
            }
        };
    }
}

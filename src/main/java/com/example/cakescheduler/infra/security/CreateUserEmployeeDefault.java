package com.example.cakescheduler.infra.security;

import com.example.cakescheduler.domain.user.UserEmployee;
import com.example.cakescheduler.repositories.UserEmployeeRepository;
import io.github.cdimascio.dotenv.Dotenv;
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
            Dotenv dotenv = Dotenv.load();
            String email = dotenv.get("DEFAULT_USER_EMPLOYEE_EMAIL");
            String password = dotenv.get("DEFAULT_USER_EMPLOYEE_PASSWORD");
            String name = dotenv.get("DEFAULT_USER_EMPLOYEE_NAME");
            String phone = dotenv.get("DEFAULT_USER_EMPLOYEE_PHONE");

            System.out.println("Checking if master user exists...");
            Optional<UserEmployee> user = repository.findByEmail(email);
            if (user.isEmpty()) {
                UserEmployee newUserEmployee = new UserEmployee();
                newUserEmployee.setPassword(passwordEncoder.encode(password));
                newUserEmployee.setEmail(email);
                newUserEmployee.setName(name);
                newUserEmployee.setPhone(phone);

                repository.save(newUserEmployee);

                String token = tokenService.generateToken(newUserEmployee);
                System.out.println("Master user saved successfully");
            } else {
                System.out.println("Master user already exists.");
            }
        };
    }
}

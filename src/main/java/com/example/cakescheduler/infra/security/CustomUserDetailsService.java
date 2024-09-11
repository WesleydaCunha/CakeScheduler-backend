package com.example.cakescheduler.infra.security;

import com.example.cakescheduler.repositories.UserEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserEmployeeRepository employeeRepository;

    @Autowired
    private UserEmployeeRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userEmployee = employeeRepository.findByEmail(username);
        if (userEmployee.isPresent()) {
            return new org.springframework.security.core.userdetails.User(userEmployee.get().getEmail(), userEmployee.get().getPassword(), new ArrayList<>());
        }
        var userClient = clientRepository.findByEmail(username);
        if (userClient.isPresent()) {
            return new org.springframework.security.core.userdetails.User(userClient.get().getEmail(), userClient.get().getPassword(), new ArrayList<>());
        }
        throw new UsernameNotFoundException("User not found");
    }
}

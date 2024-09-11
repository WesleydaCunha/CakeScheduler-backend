package com.example.cakescheduler.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.POST, "/auth/register_client").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/login_client").permitAll()
                        .requestMatchers(HttpMethod.POST, "/cake/fillings/register").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.POST, "/cake/models/register").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.POST, "/cake/complement/register").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.POST, "/cake/payment-methods/register").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.PUT, "/cake/fillings/{id}").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.PUT, "/models/{id}").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.PUT, "/cake/complement/{id}").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.PUT, "/cake/payment-methods/{id}").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.DELETE, "/cake/fillings/{id}").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.DELETE, "cake/models/{id}").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.DELETE, "/cake/complements/{id}").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.DELETE, "/cake/payment-methods/{id}").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.DELETE, "/orders/update-status/{id}").hasRole("EMPLOYEE")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

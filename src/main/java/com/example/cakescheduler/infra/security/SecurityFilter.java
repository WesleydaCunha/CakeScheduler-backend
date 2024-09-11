package com.example.cakescheduler.infra.security;

import com.auth0.jwt.JWT;
import com.example.cakescheduler.domain.user.UserClient;
import com.example.cakescheduler.domain.user.UserEmployee;
import com.example.cakescheduler.repositories.UserClientRepository;
import com.example.cakescheduler.repositories.UserEmployeeRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserEmployeeRepository userEmployeeRepository;

    @Autowired
    private UserClientRepository userClientRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = recoverToken(request);
        if (token != null) {
            var decodedJWT = JWT.decode(token);
            var login = decodedJWT.getSubject();
            var role = decodedJWT.getClaim("role").asString();

            if (login != null && role != null) {
                if (role.equals("ROLE_EMPLOYEE")) {
                    handleEmployeeAuthentication(login);
                } else if (role.equals("ROLE_CLIENT")) {
                    handleClientAuthentication(login);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private void handleEmployeeAuthentication(String login) {
        UserEmployee userEmployee = userEmployeeRepository.findByEmail(login)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        var authorities = List.of(new SimpleGrantedAuthority("ROLE_EMPLOYEE"), new SimpleGrantedAuthority("CAN_CREATE_MODEL"));
        var authentication = new UsernamePasswordAuthenticationToken(userEmployee, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void handleClientAuthentication(String login) {
        UserClient userClient = userClientRepository.findByEmail(login)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_CLIENT"));
        var authentication = new UsernamePasswordAuthenticationToken(userClient, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}

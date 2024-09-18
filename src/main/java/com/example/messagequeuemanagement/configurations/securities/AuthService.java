package com.example.messagequeuemanagement.configurations.securities;

import com.example.messagequeuemanagement.dtos.LoginRequest;
import com.example.messagequeuemanagement.dtos.UserDetailsImpl;
import com.example.messagequeuemanagement.entities.Utilisateur;
import com.example.messagequeuemanagement.implementations.UserDetailsServiceImpl;
import com.example.messagequeuemanagement.repositories.UtilisateurRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UtilisateurRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl service;

    public AuthService(UtilisateurRepository repository, JwtService jwtService, AuthenticationManager authenticationManager, UserDetailsServiceImpl service) {
        this.repository = repository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.service = service;
    }

    public Object login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getLogin(),
                        loginRequest.getPassword()
                )
        );
        UserDetailsImpl use = service.loadUserByUsername(loginRequest.getLogin());
        return jwtService.generateToken(use);
    }

    public Object refreshToken(Long id){
        return jwtService.generateRefreshToken(id);
    }

    public Utilisateur getUtilisateurByLogin(String login) throws Exception {
        return repository.findUtilisateurByLogin(login).get();
    }
}

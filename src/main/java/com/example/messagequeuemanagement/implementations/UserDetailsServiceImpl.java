package com.example.messagequeuemanagement.implementations;

import com.example.messagequeuemanagement.dtos.UserDetailsImpl;
import com.example.messagequeuemanagement.entities.Utilisateur;
import com.example.messagequeuemanagement.repositories.UtilisateurRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UtilisateurRepository repository;

    public UserDetailsServiceImpl(UtilisateurRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Utilisateur> user = repository.findUtilisateurByLogin(username);
        if(user.isEmpty())
            new UsernameNotFoundException("User with username " + username + " don't exist");
        if(!user.get().isStatus())
           throw new UsernameNotFoundException("User with username "+username+" is disable please contact administrator");
        return UserDetailsImpl.build(user.get());
    }
}

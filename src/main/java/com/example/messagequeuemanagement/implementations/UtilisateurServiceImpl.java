package com.example.messagequeuemanagement.implementations;

import com.example.messagequeuemanagement.dtos.UtilisateursDTO;
import com.example.messagequeuemanagement.entities.Utilisateur;
import com.example.messagequeuemanagement.enumerations.UserType;
import com.example.messagequeuemanagement.exceptions.UtilisateurException;
import com.example.messagequeuemanagement.repositories.UtilisateurRepository;
import com.example.messagequeuemanagement.services.UtilisateurService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UtilisateurServiceImpl implements UtilisateurService {
    UtilisateurRepository repository;
    PasswordEncoder passwordEncoder;
    @Override
    public void createUser(UtilisateursDTO utilisateurRecord) throws UtilisateurException {
        if(repository.findUtilisateurByLogin(utilisateurRecord.getLogin()).isPresent())
            throw new UtilisateurException("UserName "+utilisateurRecord.getLogin()+" already exist", 400);
        Utilisateur utilisateur = Utilisateur.builder()
                .login(utilisateurRecord.getLogin())
                .name(utilisateurRecord.getName())
                .userType(utilisateurRecord.getUserType())
                .password(passwordEncoder.encode("1234567890"))
                .status(Boolean.FALSE.booleanValue())
                .build();
        repository.save(utilisateur);
    }

    @Override
    public void createAdminUser(UtilisateursDTO utilisateurRecord) throws UtilisateurException {
        if(repository.findUtilisateurByLogin(utilisateurRecord.getLogin()).isPresent())
            throw new UtilisateurException("UserName "+utilisateurRecord.getLogin()+" already exist", 400);
        Utilisateur utilisateur = Utilisateur.builder()
                .login(utilisateurRecord.getLogin())
                .name(utilisateurRecord.getName())
                .userType(utilisateurRecord.getUserType())
                .password(passwordEncoder.encode(utilisateurRecord.getPassword()))
                .status(Boolean.TRUE.booleanValue())
                .build();
        repository.save(utilisateur);
    }

    @Override
    public void updateUser(Long id, String name) throws UtilisateurException {
        Utilisateur utilisateur = repository.getReferenceById(id);
        if(utilisateur == null)
            throw new UtilisateurException("User with Id = "+id+" not found", 404);
        utilisateur.setName(name);
        repository.save(utilisateur);
    }

    @Override
    public void disableUser(Long id) throws UtilisateurException {
        Utilisateur utilisateur = repository.getReferenceById(id);
        if(utilisateur == null)
            throw new UtilisateurException("User with Id = "+id+" not found", 404);
        utilisateur.setStatus(Boolean.FALSE.booleanValue());
        repository.save(utilisateur);
    }

    @Override
    public void enableUser(Long id) throws UtilisateurException {
        Utilisateur utilisateur = repository.getReferenceById(id);
        if(utilisateur == null)
            throw new UtilisateurException("User with Id = "+id+" not found", 404);
        utilisateur.setStatus(Boolean.TRUE.booleanValue());
        repository.save(utilisateur);
    }

    @Override
    public Utilisateur findUserById(Long id) throws UtilisateurException {
        Utilisateur utilisateur = repository.getReferenceById(id);
        if(utilisateur == null)
            throw new UtilisateurException("User with Id = "+id+" not found", 404);
        return utilisateur;
    }

    @Override
    public Page<Utilisateur> findAllUserByUserType(UserType userType, int page, int size) throws UtilisateurException {
        return repository.findUtilisateursByUserTypeIsNot(userType, PageRequest.of(page, size));
    }

    @Override
    public Page<Utilisateur> findAll(int page, int size) throws UtilisateurException {
        return repository.findAll(PageRequest.of(page, size));
    }

    @Override
    public List<Utilisateur> findAll() throws UtilisateurException {
        return repository.findAll();
    }
}

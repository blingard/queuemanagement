package com.example.messagequeuemanagement.services;

import com.example.messagequeuemanagement.dtos.UtilisateursDTO;
import com.example.messagequeuemanagement.entities.Utilisateur;
import com.example.messagequeuemanagement.enumerations.UserType;
import com.example.messagequeuemanagement.exceptions.UtilisateurException;
import com.example.messagequeuemanagement.records.UtilisateurRecord;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UtilisateurService {
    void createUser(UtilisateursDTO utilisateurRecord) throws UtilisateurException;
    void createAdminUser(UtilisateursDTO utilisateurRecord) throws UtilisateurException;
    void updateUser(Long id, String name) throws UtilisateurException;
    void disableUser(Long id) throws UtilisateurException;
    void enableUser(Long id) throws UtilisateurException;
    void changeStatus(Long id) throws UtilisateurException;
    void saveFile(MultipartFile file) throws UtilisateurException;
    Utilisateur findUserById(Long id)  throws UtilisateurException;
    Page<Utilisateur> findAllUserByUserType(UserType userType, int page, int size)  throws UtilisateurException;
    Page<Utilisateur> findAll(int page, int size) ;
    List<Utilisateur> findAll();


}

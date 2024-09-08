package com.example.messagequeuemanagement.apis;

import com.example.messagequeuemanagement.dtos.UtilisateursDTO;
import com.example.messagequeuemanagement.entities.Utilisateur;
import com.example.messagequeuemanagement.enumerations.UserType;
import com.example.messagequeuemanagement.exceptions.UtilisateurException;
import com.example.messagequeuemanagement.services.UtilisateurService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UtilisateurRestController {
    UtilisateurService service;

    @PostMapping
    public ResponseEntity<Boolean> createUser(@RequestBody UtilisateursDTO utilisateurRecord) throws UtilisateurException{
        service.createUser(utilisateurRecord);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @PutMapping("{id}/{name}")
    public ResponseEntity<Boolean> updateUser(@PathVariable("id") Long id, @PathVariable("name") String name) throws UtilisateurException{
        service.updateUser(id, name);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @PutMapping("disabled/{id}")
    public ResponseEntity<Boolean> disableUser(@PathVariable("id") Long id) throws UtilisateurException{
        service.disableUser(id);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @PutMapping("enabled/{id}")
    public ResponseEntity<Boolean> enableUser(@PathVariable("id") Long id) throws UtilisateurException{
        service.enableUser(id);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @GetMapping("{id}")
    public ResponseEntity<Utilisateur> findUserById(@PathVariable("id") Long id) throws UtilisateurException{
        return ResponseEntity.ok(service.findUserById(id));
    }

    @GetMapping("{userType}/{page}/{size}")
    public ResponseEntity<Page<Utilisateur>> findAllUserByUserType(
            @PathVariable("userType") UserType userType,
            @PathVariable("page") int page,
            @PathVariable("size") int size) throws UtilisateurException{
        return ResponseEntity.ok(service.findAllUserByUserType(userType, page, size));
    }

    @GetMapping("{page}/{size}")
    public ResponseEntity<Page<Utilisateur>> findAll(
            @PathVariable("page") int page,
            @PathVariable("size") int size
    ) throws UtilisateurException{
        return ResponseEntity.ok(service.findAll(page, size));
    }

}

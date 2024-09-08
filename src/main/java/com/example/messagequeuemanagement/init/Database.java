package com.example.messagequeuemanagement.init;

import com.example.messagequeuemanagement.dtos.UtilisateursDTO;
import com.example.messagequeuemanagement.enumerations.UserType;
import com.example.messagequeuemanagement.services.UtilisateurService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Database implements CommandLineRunner {
    private final UtilisateurService iUtilisateur;

    public Database(UtilisateurService iUtilisateur) {
        this.iUtilisateur = iUtilisateur;
    }

    @Override
    public void run(String... args) throws Exception {
        try{
            UtilisateursDTO utilisateurRecord = new UtilisateursDTO(
                    null,
                    "admin",
            "admin",
            "123456",
            UserType.ADMIN,
            LocalDateTime.now(),
            Boolean.TRUE
            );
            iUtilisateur.createAdminUser(utilisateurRecord);
        }catch (Exception e){}






    }
}

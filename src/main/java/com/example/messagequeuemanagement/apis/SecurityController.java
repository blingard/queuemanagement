package com.example.messagequeuemanagement.apis;

import com.example.messagequeuemanagement.configurations.securities.AuthService;
import com.example.messagequeuemanagement.dtos.LoginRequest;
import com.example.messagequeuemanagement.entities.Utilisateur;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/auth")
public class SecurityController {
    private final AuthService service;

    public SecurityController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception {
        HttpHeaders  httpHeaders = new HttpHeaders();
        Map<String, Object> data = new HashMap<>();
        String accessToken = "Bearer "+service.login(loginRequest);
        data.put("accessToken", accessToken);
        Utilisateur utilisateur = service.getUtilisateurByLogin(loginRequest.getLogin());
        String refreshToken = "Bearer "+service.refreshToken(utilisateur.getId());
        data.put("refreshToken", refreshToken);
        return new ResponseEntity<>(data, httpHeaders, HttpStatus.OK);
    }

}

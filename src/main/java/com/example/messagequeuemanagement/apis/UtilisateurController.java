package com.example.messagequeuemanagement.apis;

import com.example.messagequeuemanagement.dtos.UtilisateursDTO;
import com.example.messagequeuemanagement.entities.Utilisateur;
import com.example.messagequeuemanagement.enumerations.UserType;
import com.example.messagequeuemanagement.exceptions.UtilisateurException;
import com.example.messagequeuemanagement.services.UtilisateurService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("v2/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UtilisateurController {
    UtilisateurService service;


    @GetMapping()
    public String findAll(
            Model model,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size) throws UtilisateurException{
        Page<Utilisateur> listUser = service.findAll(page, size);
        int[] pages = new int[listUser.getTotalPages()];
        model.addAttribute("users", listUser.getContent());
        model.addAttribute("pages", pages);

        model.addAttribute("size", size);
        model.addAttribute("pageCourante", pages);
        return "user/list";
    }

    @GetMapping("create")
    public String createUsers(Model model){
        model.addAttribute("user", new UtilisateursDTO());
        model.addAttribute("userTypes", UserType.values());
        return "user/form";
    }

    @PostMapping("save")
    public String saveUsers(Model model, @Valid UtilisateursDTO user, BindingResult bindingResult) throws UtilisateurException {
        if(bindingResult.hasErrors()){
            return "user/form";
        }
        try {
            service.createUser(user);
            model.addAttribute("user", user);
            return "user/confirmation";
        }catch (Exception ex){
            return "user/form";
        }
    }



}

package com.example.messagequeuemanagement.apis;

import com.example.messagequeuemanagement.dtos.UtilisateursDTO;
import com.example.messagequeuemanagement.entities.Motifs;
import com.example.messagequeuemanagement.entities.Utilisateur;
import com.example.messagequeuemanagement.enumerations.UserType;
import com.example.messagequeuemanagement.exceptions.MotifException;
import com.example.messagequeuemanagement.exceptions.UtilisateurException;
import com.example.messagequeuemanagement.model.Greeting;
import com.example.messagequeuemanagement.model.Message;
import com.example.messagequeuemanagement.records.MotifRecord;
import com.example.messagequeuemanagement.services.MotifsService;
import com.example.messagequeuemanagement.services.UtilisateurService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Controller
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WebController {

    private final Path root = Paths.get("uploads");

    UtilisateurService service;
    MotifsService motifsService;

    public WebController(UtilisateurService service, MotifsService motifsService) {
        this.service = service;
        this.motifsService = motifsService;
        try {
            Files.createDirectory(root);
        } catch (IOException e) {}
    }

    @GetMapping("/")
    public String index() {
        return "admin/index";
    }
    @GetMapping("/admin/actions/profile")
    public String profile() {
        return "admin/profile";
    }
    @GetMapping("/admin/actions/list")
    public String list(
            Model model,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size) {
        Page<Utilisateur> listUser = service.findAll(page, size);
        int[] pages = new int[listUser.getTotalPages()];
        model.addAttribute("users", listUser.getContent());
        model.addAttribute("pages", pages);

        model.addAttribute("size", size);
        model.addAttribute("pageCourante", pages);

        return "admin/users/list";
    }

    @GetMapping("/admin/motifs/list")
    public String listMotif(
            Model model,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size) {
        Page<Motifs> listMotifs = motifsService.findAll(page, size);
        int[] pages = new int[listMotifs.getTotalPages()];
        model.addAttribute("motifs", listMotifs.getContent());
        model.addAttribute("pages", pages);

        model.addAttribute("size", size);
        model.addAttribute("pageCourante", pages);

        return "admin/motifs/list";
    }
    @GetMapping("/cashier/login")
    public String connect(Model model) {
        List<Utilisateur> listUser = service.findAll().stream().filter(utilisateur -> (utilisateur.isStatus())&&!(utilisateur.getUserType().equals(UserType.ADMIN))).toList();
        model.addAttribute("users", listUser);
        return "other/index";
    }
    @GetMapping("/admin/actions/change_status")
    public String changeStatus( @RequestParam(name = "id") Long id) {
        try {
            service.changeStatus(id);
        } catch (UtilisateurException e) {
        }
        return "redirect:/admin/actions/list";
    }
    @GetMapping("/admin/motifs/change_status")
    public String changeStatusMotif( @RequestParam(name = "id") Long id) {
        try {
            motifsService.changeStatus(id);
        } catch (MotifException e) {
        }
        return "redirect:/admin/motifs/list";
    }

    @GetMapping("/admin/logout")
    public String logout() {
        return "redirect:/admin/login";
    }

    @GetMapping("/admin/login")
    public String login() {
        return "admin/index";
    }

    @GetMapping("/admin/actions/create")
    public String create(Model model){
        model.addAttribute("user", new UtilisateursDTO());
        model.addAttribute("userTypes", UserType.values());
        return "admin/users/form";
    }

    @PostMapping("/admin/actions/save")
    public String saveUsers(Model model, @Valid UtilisateursDTO user, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "admin/users/form";
        }
        try {
            service.createUser(user);
            model.addAttribute("user", user);
            return "admin/users/confirmation";
        }catch (Exception ex){
            return "admin/users/form";
        }
    }

    @PostMapping("/admin/motifs/save")
    public String saveMotifs(Model model, MotifRecord motifRecord, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "admin/users/form";
        }
        try {
            motifsService.createMotif(motifRecord);
            model.addAttribute("motif", motifRecord);
            return "admin/motifs/confirmation";
        }catch (Exception ex){
            return "admin/motifs/form";
        }
    }

    @GetMapping("/admin/motifs/create")
    public String createMotif(Model model){
        model.addAttribute("motif", new MotifRecord(null, null, null, null));
        return "admin/motifs/form";
    }

    @GetMapping("/other")
    public String action() {

        //set tohent
        return "other/action";
    }
    @GetMapping("/webchat")
    public String webchat(Model model, Authentication authentication) {
        model.addAttribute("username", authentication.getName());
        return "webchat";
    }

    @MessageMapping("/call")
    @SendTo("/topic/greetings")
    public Greeting call(Message message) throws Exception {
        // Thread.sleep(1000); // simulated delay
        return new Greeting("Caisse 1", message.name(), message.id());
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(Message message) throws Exception {
        // Thread.sleep(1000); // simulated delay
        return new Greeting("Caisse 1", message.name(), message.id());
    }
}

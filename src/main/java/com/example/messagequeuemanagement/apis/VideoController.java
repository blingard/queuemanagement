package com.example.messagequeuemanagement.apis;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class VideoController {

    private final Path rootVideo = Paths.get("uploads/video");
    private final Path rootAudio = Paths.get("uploads/audio");

    public VideoController() {
        try {
            Files.createDirectory(rootVideo);
            Files.createDirectory(rootAudio);
        } catch (IOException e) {

        }
    }

    @GetMapping("/admin/actions/file")
    public String index(Model model) {
        try {
            model.addAttribute("videos", Files.walk(this.rootVideo, 1)
                    .filter(path -> !path.equals(this.rootVideo))
                    .map(this.rootVideo::relativize)
                    .map(path -> MvcUriComponentsBuilder.fromMethodName(VideoController.class, "serveVideo", path.getFileName().toString()).build().toString())
                    .toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "admin/users/files";
    }

    @PostMapping("/admin/actions/saveFile")
    public String uploadVideo(@RequestParam("file") MultipartFile file, Model model) {
        try {
            deleteAllFiles();
            Files.copy(file.getInputStream(), this.rootVideo.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin/actions/list";
    }

    @GetMapping("file/video/{filename:.+}")
    public ResponseEntity<Resource> serveVideo(@PathVariable String filename) {
        try {
            Path file = rootVideo.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType("video/mp4"))
                        .body(resource);
            } else {
                throw new RuntimeException("Fichier non trouvé");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Erreur dans le téléchargement de la vidéo", e);
        }
    }

    @GetMapping("file/audio{filename:.+}")
    public ResponseEntity<Resource> serveAudio(@PathVariable String filename) {
        try {
            Path file = rootAudio.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType("audio/mp3"))
                        .body(resource);
            } else {
                throw new RuntimeException("Fichier non trouvé");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Erreur dans le téléchargement de la vidéo", e);
        }
    }

    private void deleteAllFiles() {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(rootVideo)) {
            for (Path entry : stream) {
                if (Files.isRegularFile(entry)) {
                    Files.delete(entry);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/home")
    public String monitor(Model model) {
        try {
            model.addAttribute("video", Files.walk(this.rootVideo, 1)
                    .filter(path -> !path.equals(this.rootVideo))
                    .map(this.rootVideo::relativize)
                    .map(path -> MvcUriComponentsBuilder.fromMethodName(VideoController.class, "serveVideo", path.getFileName().toString()).build().toString())
                    .toList().get(0));
            model.addAttribute("audio", Files.walk(this.rootAudio, 1)
                    .filter(path -> !path.equals(this.rootAudio))
                    .map(this.rootAudio::relativize)
                    .map(path -> MvcUriComponentsBuilder.fromMethodName(VideoController.class, "serveAudio", path.getFileName().toString()).build().toString())
                    .toList().get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "client/monitor";
    }

    @GetMapping("/ticket")
    public String ticket(){
        return "client/index";
    }
}


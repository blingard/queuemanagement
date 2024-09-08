package com.example.messagequeuemanagement.apis;

import com.example.messagequeuemanagement.entities.Motifs;
import com.example.messagequeuemanagement.exceptions.MotifException;
import com.example.messagequeuemanagement.exceptions.UtilisateurException;
import com.example.messagequeuemanagement.records.MotifRecord;
import com.example.messagequeuemanagement.services.MotifsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/motifs")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MotifsController {

    MotifsService service;

    @PostMapping("save")
    public ResponseEntity<Boolean> createMotif(@RequestBody MotifRecord motifRecord) throws MotifException, UtilisateurException{
        service.createMotif(motifRecord);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @PutMapping("{id}/{name}")
    public ResponseEntity<Boolean> updateMotif(@PathVariable("id") Long id, @PathVariable("name") String name) throws MotifException{
        service.updateMotif(id, name);
        return ResponseEntity.ok(Boolean.TRUE);
    }
    @PutMapping("disable/{id}")
    public ResponseEntity<Boolean> disableMotif(@PathVariable("id") Long id) throws MotifException{
        service.disableMotif(id);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @PutMapping("enable/{id}")
    public ResponseEntity<Boolean> enableMotif(@PathVariable("id") Long id) throws MotifException{
        service.enableMotif(id);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @GetMapping("{id}")
    public ResponseEntity<Motifs> findMotifById(@PathVariable("id") Long id) throws MotifException{
        return ResponseEntity.ok(service.findMotifById(id));
    }

    @GetMapping("all")
    public ResponseEntity<List<Motifs>> findAllMotif() throws MotifException{
        return ResponseEntity.ok(service.findAllMotif());
    }
}

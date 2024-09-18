package com.example.messagequeuemanagement.implementations;

import com.example.messagequeuemanagement.entities.Motifs;
import com.example.messagequeuemanagement.entities.Utilisateur;
import com.example.messagequeuemanagement.enumerations.UserType;
import com.example.messagequeuemanagement.exceptions.MotifException;
import com.example.messagequeuemanagement.exceptions.UtilisateurException;
import com.example.messagequeuemanagement.records.MotifRecord;
import com.example.messagequeuemanagement.repositories.MotifsRepository;
import com.example.messagequeuemanagement.repositories.UtilisateurRepository;
import com.example.messagequeuemanagement.services.MotifsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MotifServiceImpl implements MotifsService {

    MotifsRepository repository;
    UtilisateurRepository utilisateurRepository;

    @Override
    public void createMotif(MotifRecord motifRecord) throws MotifException, UtilisateurException {
        Utilisateur utilisateur = utilisateurRepository.getReferenceById(motifRecord.userId());
        if(utilisateur==null)
            throw new UtilisateurException("User not found", 404);
        if(utilisateur.getUserType()!= UserType.ADMIN)
            throw new UtilisateurException("User not allowed to this operation", 404);
        Motifs motifs = Motifs.builder()
                .active(Boolean.FALSE.booleanValue())
                .initial(motifRecord.initiale())
                .name(motifRecord.name())
                .createur(utilisateur)
                .build();
        repository.save(motifs);
    }

    @Override
    public void updateMotif(Long id, String name) throws MotifException {

    }

    @Override
    public void disableMotif(Long id) throws MotifException {
        Optional<Motifs> motifsOptional = repository.findById(id);
        if(motifsOptional.isEmpty())
            throw new MotifException("Motif not found", 404);
        Motifs motifs = motifsOptional.get();
        motifs.setActive(Boolean.FALSE);
        repository.save(motifs);
    }

    @Override
    public void enableMotif(Long id) throws MotifException {
        Optional<Motifs> motifsOptional = repository.findById(id);
        if(motifsOptional.isEmpty())
            throw new MotifException("Motif not found", 404);
        Motifs motifs = motifsOptional.get();
        motifs.setActive(Boolean.TRUE);
        repository.save(motifs);
    }

    @Override
    public void changeStatus(Long id) throws MotifException{
        Motifs motifs = repository.getReferenceById(id);
        if(motifs == null)
            throw new MotifException("User with Id = "+id+" not found", 404);
        motifs.setActive(!motifs.isActive());
        repository.save(motifs);

    }

    @Override
    public Motifs findMotifById(Long id) throws MotifException {
        Optional<Motifs> motifsOptional = repository.findById(id);
        if(motifsOptional.isEmpty())
            throw new MotifException("Motif not found", 404);
        return motifsOptional.get();
    }

    @Override
    public List<Motifs> findAllActiveMotif() throws MotifException {
        return repository.findMotifsByActive(Boolean.TRUE);
    }

    @Override
    public List<Motifs> findAllMotif() throws MotifException {
        return repository.findAll();
    }

    @Override
    public Page<Motifs> findAll(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }
}

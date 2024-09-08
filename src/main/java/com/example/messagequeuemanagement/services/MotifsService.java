package com.example.messagequeuemanagement.services;

import com.example.messagequeuemanagement.dtos.MotifsDTO;
import com.example.messagequeuemanagement.entities.Motifs;
import com.example.messagequeuemanagement.exceptions.MotifException;
import com.example.messagequeuemanagement.exceptions.UtilisateurException;
import com.example.messagequeuemanagement.records.MotifRecord;

import java.util.List;

public interface MotifsService {
    void createMotif(MotifRecord motifRecord) throws MotifException, UtilisateurException;
    void updateMotif(Long id, String name) throws MotifException;
    void disableMotif(Long id) throws MotifException;
    void enableMotif(Long id) throws MotifException;
    Motifs findMotifById(Long id)  throws MotifException;
    List<Motifs> findAllMotif()  throws MotifException;


}

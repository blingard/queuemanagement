package com.example.messagequeuemanagement.services;

import com.example.messagequeuemanagement.dtos.FichiersVideoDTO;
import com.example.messagequeuemanagement.exceptions.FileException;
import org.springframework.web.multipart.MultipartFile;

public interface FilesService {
    void createFile(FichiersVideoDTO fichiersVideoDTO, MultipartFile multipartFile) throws FileException;
    void updateFile(Long id, String name) throws FileException;
    MultipartFile getFile(Long id) throws FileException;
}

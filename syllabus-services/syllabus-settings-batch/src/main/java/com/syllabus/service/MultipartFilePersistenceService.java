package com.syllabus.service;

import org.springframework.web.multipart.MultipartFile;

public interface MultipartFilePersistenceService {

    public void save(MultipartFile file);
    
}

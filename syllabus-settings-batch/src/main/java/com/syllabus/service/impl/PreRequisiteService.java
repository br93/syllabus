package com.syllabus.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.syllabus.converter.impl.PreRequisiteCsvConverter;
import com.syllabus.exception.CsvException;
import com.syllabus.model.PreRequisiteModel;
import com.syllabus.repository.PreRequisiteRepository;
import com.syllabus.service.MultipartFilePersistenceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PreRequisiteService implements MultipartFilePersistenceService{

    private final PreRequisiteCsvConverter csvConverter;
    private final PreRequisiteRepository preRequisiteRepository;

    public void save(MultipartFile file) {
        try {
            List<PreRequisiteModel> preRequisites = csvConverter.csvToModel(file.getInputStream());
            preRequisiteRepository.saveAll(preRequisites);
        } catch (IOException ex) {
            throw new CsvException("fail to store csv data: " + ex.getMessage());
        }
    }

}

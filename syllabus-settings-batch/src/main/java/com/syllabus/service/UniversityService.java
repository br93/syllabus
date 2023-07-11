package com.syllabus.service;

import org.springframework.stereotype.Service;

import com.syllabus.exception.EntityNotFoundException;
import com.syllabus.model.UniversityModel;
import com.syllabus.repository.UniversityRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UniversityService {

    private final UniversityRepository universityRepository;

    public UniversityModel findByUniversityCode(String code) {
        return universityRepository.findByUniversityCode(code)
                .orElseThrow(() -> new EntityNotFoundException("university " + code + " not found"));
    }
}

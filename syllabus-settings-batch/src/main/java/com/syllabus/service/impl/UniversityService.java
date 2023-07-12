package com.syllabus.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.syllabus.converter.impl.UniversityCsvConverter;
import com.syllabus.exception.CsvException;
import com.syllabus.exception.EntityNotFoundException;
import com.syllabus.model.UniversityModel;
import com.syllabus.repository.UniversityRepository;
import com.syllabus.service.MultipartFilePersistenceService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UniversityService implements MultipartFilePersistenceService {

	private final UniversityRepository universityRepository;
	private final UniversityCsvConverter csvConverter;

	public void save(MultipartFile file) {
		try {
			List<UniversityModel> universities = csvConverter.csvToModel(file.getInputStream());
			universityRepository.saveAll(universities);
		} catch (IOException ex) {
			throw new CsvException("fail to store csv data: " + ex.getMessage());
		}
	}

	public UniversityModel findByUniversityCode(String code) {
		return universityRepository.findByUniversityCode(code)
				.orElseThrow(() -> new EntityNotFoundException("university " + code + " not found"));
	}
}

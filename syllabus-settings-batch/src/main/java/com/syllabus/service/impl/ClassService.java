package com.syllabus.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.syllabus.converter.impl.ClassCsvConverter;
import com.syllabus.exception.CsvException;
import com.syllabus.exception.EntityNotFoundException;
import com.syllabus.model.ClassModel;
import com.syllabus.repository.ClassRepository;
import com.syllabus.service.MultipartFilePersistenceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClassService implements MultipartFilePersistenceService{

	private final ClassRepository classRepository;
	private final ClassCsvConverter csvConverter;

	public void save(MultipartFile file) {
		try {
			List<ClassModel> classes = csvConverter.csvToModel(file.getInputStream());
			classRepository.saveAll(classes);
		} catch (IOException ex) {
			throw new CsvException("fail to store csv data: " + ex.getMessage());
		}
	}

	public ClassModel findByClassCode(String code) {
		return classRepository.findByClassCode(code)
				.orElseThrow(() -> new EntityNotFoundException("class " + code + " not found"));
	}
}

package com.syllabus.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.syllabus.converter.impl.CourseTypeCsvConverter;
import com.syllabus.exception.CsvException;
import com.syllabus.exception.EntityNotFoundException;
import com.syllabus.model.CourseTypeModel;
import com.syllabus.repository.CourseTypeRepository;
import com.syllabus.service.MultipartFilePersistenceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseTypeService implements MultipartFilePersistenceService{

	private final CourseTypeRepository courseTypeRepository;
	private final CourseTypeCsvConverter csvConverter;

	public void save(MultipartFile file) {
		try {
			List<CourseTypeModel> courseTypes = csvConverter.csvToModel(file.getInputStream());
			courseTypeRepository.saveAll(courseTypes);
		} catch (IOException ex) {
			throw new CsvException("fail to store csv data: " + ex.getMessage());
		}
	}

	public CourseTypeModel findByTypeName(String name) {
		return courseTypeRepository.findByTypeName(name)
				.orElseThrow(() -> new EntityNotFoundException("type " + name + " not found"));
	}
}

package com.syllabus.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.syllabus.converter.impl.CourseCsvConverter;
import com.syllabus.exception.CsvException;
import com.syllabus.exception.EntityNotFoundException;
import com.syllabus.model.CourseModel;
import com.syllabus.repository.CourseRepository;
import com.syllabus.service.MultipartFilePersistenceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseService implements MultipartFilePersistenceService{

	private final CourseRepository courseRepository;
	private final CourseCsvConverter csvConverter;

	public void save(MultipartFile file) {
		try {
			List<CourseModel> courses = csvConverter.csvToModel(file.getInputStream());
			courseRepository.saveAll(courses);
		} catch (IOException ex) {
			throw new CsvException("fail to store csv data: " + ex.getMessage());
		}
	}

	public CourseModel findByCourseCode(String code) {
		return courseRepository.findByCourseCode(code)
				.orElseThrow(() -> new EntityNotFoundException("course " + code + " not found"));
	}
}

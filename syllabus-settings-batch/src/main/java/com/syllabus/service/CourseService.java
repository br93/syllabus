package com.syllabus.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.syllabus.helper.CSVHelper;
import com.syllabus.model.CourseModel;
import com.syllabus.repository.CourseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseService {
	
	private final CourseRepository courseRepository;
	private final CSVHelper csvHelper;

	public void save(MultipartFile file){
		try {
			List<CourseModel> courses = csvHelper.csvToCourse(file.getInputStream());
			courseRepository.saveAll(courses);
		} catch (IOException ex) {
			throw new RuntimeException("fail to store csv data: " + ex.getMessage());
		}
	}
}

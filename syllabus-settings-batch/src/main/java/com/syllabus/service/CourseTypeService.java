package com.syllabus.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.syllabus.helper.CSVHelper;
import com.syllabus.model.CourseTypeModel;
import com.syllabus.repository.CourseTypeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseTypeService {
	
	private final CourseTypeRepository courseTypeRepository;
	private final CSVHelper csvHelper;

	public void save(MultipartFile file){
		try {
			List<CourseTypeModel> courseTypes = csvHelper.csvToCourseType(file.getInputStream());
			courseTypeRepository.saveAll(courseTypes);
		} catch (IOException ex) {
			throw new RuntimeException("fail to store csv data: " + ex.getMessage());
		}
	}
}

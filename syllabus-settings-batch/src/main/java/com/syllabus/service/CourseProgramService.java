package com.syllabus.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.syllabus.helper.CSVHelper;
import com.syllabus.model.CourseProgramModel;
import com.syllabus.repository.CourseProgramRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseProgramService {
	
	private final CourseProgramRepository courseProgramRepository;
	private final CSVHelper csvHelper;

	public void save(MultipartFile file){
		try {
			List<CourseProgramModel> coursePrograms = csvHelper.csvToCourseProgram(file.getInputStream());
			saveFromList(coursePrograms);
		} catch (IOException ex) {
			throw new RuntimeException("fail to store csv data: " + ex.getMessage());
		}
	}
	
	private void saveFromList(List<CourseProgramModel> list) {
		Integer size = list.size();
		
		for (int i = 0; i < size; i++) {
			courseProgramRepository.save(list.get(i));
		}
	}
}

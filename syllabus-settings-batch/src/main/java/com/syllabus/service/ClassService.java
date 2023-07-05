package com.syllabus.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.syllabus.helper.CSVHelper;
import com.syllabus.model.ClassModel;
import com.syllabus.repository.ClassRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClassService {
	
	private final ClassRepository classRepository;
	private final CSVHelper csvHelper;

	public void save(MultipartFile file){
		try {
			List<ClassModel> classes = csvHelper.csvToClass(file.getInputStream());
			classRepository.saveAll(classes);
		} catch (IOException ex) {
			throw new RuntimeException("fail to store csv data: " + ex.getMessage());
		}
	}
}

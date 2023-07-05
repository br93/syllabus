package com.syllabus.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.syllabus.helper.CSVHelper;
import com.syllabus.model.ClassScheduleModel;
import com.syllabus.repository.ClassScheduleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClassScheduleService {
	
	private final ClassScheduleRepository classScheduleRepository;
	private final CSVHelper csvHelper;

	public void save(MultipartFile file){
		try {
			List<ClassScheduleModel> classSchedules = csvHelper.csvToClassSchedule(file.getInputStream());
			classScheduleRepository.saveAll(classSchedules);
		} catch (IOException ex) {
			throw new RuntimeException("fail to store csv data: " + ex.getMessage());
		}
	}
}

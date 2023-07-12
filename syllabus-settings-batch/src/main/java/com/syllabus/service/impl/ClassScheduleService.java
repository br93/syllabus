package com.syllabus.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.syllabus.converter.impl.ClassScheduleCsvConverter;
import com.syllabus.exception.CsvException;
import com.syllabus.exception.EntityNotFoundException;
import com.syllabus.model.ClassScheduleModel;
import com.syllabus.repository.ClassScheduleRepository;
import com.syllabus.service.MultipartFilePersistenceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClassScheduleService implements MultipartFilePersistenceService{

	private final ClassScheduleRepository classScheduleRepository;
	private final ClassScheduleCsvConverter csvConverter;

	public void save(MultipartFile file) {
		try {
			List<ClassScheduleModel> classSchedules = csvConverter.csvToModel(file.getInputStream());
			classScheduleRepository.saveAll(classSchedules);
		} catch (IOException ex) {
			throw new CsvException("fail to store csv data: " + ex.getMessage());
		}
	}

	public ClassScheduleModel findByClassScheduleId(String id) {
		return classScheduleRepository.findByClassScheduleId(id)
				.orElseThrow(() -> new EntityNotFoundException("class schedule not found"));
	}
}

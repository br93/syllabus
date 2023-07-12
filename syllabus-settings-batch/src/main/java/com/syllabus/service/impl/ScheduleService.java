package com.syllabus.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.syllabus.converter.impl.ScheduleCsvConverter;
import com.syllabus.exception.CsvException;
import com.syllabus.exception.EntityNotFoundException;
import com.syllabus.model.ScheduleModel;
import com.syllabus.repository.ScheduleRepository;
import com.syllabus.service.MultipartFilePersistenceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleService implements MultipartFilePersistenceService{

	private final ScheduleRepository scheduleRepository;
	private final ScheduleCsvConverter csvConverter;

	public void save(MultipartFile file) {
		try {
			List<ScheduleModel> schedules = csvConverter.csvToModel(file.getInputStream());
			scheduleRepository.saveAll(schedules);
		} catch (IOException ex) {
			throw new CsvException("fail to store csv data: " + ex.getMessage());
		}
	}

	public ScheduleModel findByScheduleCode(String code) {
		return scheduleRepository.findByScheduleCode(code)
				.orElseThrow(() -> new EntityNotFoundException("schedule " + code + " not found"));
	}
}

package com.syllabus.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.syllabus.helper.CSVHelper;
import com.syllabus.model.ScheduleModel;
import com.syllabus.repository.ScheduleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleService {
	
	private final ScheduleRepository scheduleRepository;
	private final CSVHelper csvHelper;

	public void save(MultipartFile file){
		try {
			List<ScheduleModel> schedules = csvHelper.csvToSchedule(file.getInputStream());
			scheduleRepository.saveAll(schedules);
		} catch (IOException ex) {
			throw new RuntimeException("fail to store csv data: " + ex.getMessage());
		}
	}
}

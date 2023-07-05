package com.syllabus.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.syllabus.helper.CSVHelper;
import com.syllabus.model.DayModel;
import com.syllabus.repository.DayRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DayService {
	
	private final DayRepository dayRepository;
	private final CSVHelper csvHelper;

	public void save(MultipartFile file){
		try {
			List<DayModel> days = csvHelper.csvToDay(file.getInputStream());
			dayRepository.saveAll(days);
		} catch (IOException ex) {
			throw new RuntimeException("fail to store csv data: " + ex.getMessage());
		}
	}
}

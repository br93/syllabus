package com.syllabus.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.syllabus.converter.impl.DayCsvConverter;
import com.syllabus.exception.CsvException;
import com.syllabus.exception.EntityNotFoundException;
import com.syllabus.model.DayModel;
import com.syllabus.repository.DayRepository;
import com.syllabus.service.MultipartFilePersistenceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DayService implements MultipartFilePersistenceService{

	private final DayRepository dayRepository;
	private final DayCsvConverter csvConverter;

	public void save(MultipartFile file) {
		try {
			List<DayModel> days = csvConverter.csvToModel(file.getInputStream());
			dayRepository.saveAll(days);
		} catch (IOException ex) {
			throw new CsvException("fail to store csv data: " + ex.getMessage());
		}
	}

	public DayModel findByDayNumber(Short number) {
		return dayRepository.findByDayNumber(number)
				.orElseThrow(() -> new EntityNotFoundException("day " + number + " not found"));
	}
}

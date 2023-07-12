package com.syllabus.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.syllabus.converter.impl.CourseProgramCsvConverter;
import com.syllabus.exception.CsvException;
import com.syllabus.exception.EntityNotFoundException;
import com.syllabus.model.CourseProgramModel;
import com.syllabus.repository.CourseProgramRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseProgramService {

	private final CourseProgramRepository courseProgramRepository;
	private final CourseProgramCsvConverter csvConverter;

	public void save(MultipartFile file) {
		try {
			List<CourseProgramModel> coursePrograms = csvConverter.csvToModel(file.getInputStream());
			courseProgramRepository.saveAll(coursePrograms);
		} catch (IOException ex) {
			throw new CsvException("fail to store csv data: " + ex.getMessage());
		}
	}

	public CourseProgramModel findByCourseProgramId(String id) {
		return courseProgramRepository.findByCourseProgramId(id)
				.orElseThrow(() -> new EntityNotFoundException("course program not found"));
	}
}

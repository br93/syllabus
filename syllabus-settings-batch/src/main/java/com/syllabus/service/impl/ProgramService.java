package com.syllabus.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.syllabus.converter.impl.ProgramCsvConverter;
import com.syllabus.exception.CsvException;
import com.syllabus.exception.EntityNotFoundException;
import com.syllabus.model.ProgramModel;
import com.syllabus.repository.ProgramRepository;
import com.syllabus.service.MultipartFilePersistenceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProgramService implements MultipartFilePersistenceService{

	private final ProgramRepository programRepository;
	private final ProgramCsvConverter csvConverter;

	public void save(MultipartFile file) {
		try {
			List<ProgramModel> programs = csvConverter.csvToModel(file.getInputStream());
			programRepository.saveAll(programs);
		} catch (IOException ex) {
			throw new CsvException("fail to store csv data: " + ex.getMessage());
		}
	}

	public ProgramModel findByProgramCode(String code) {
		return programRepository.findByProgramCode(code)
				.orElseThrow(() -> new EntityNotFoundException("program " + code + " not found"));
	}
}

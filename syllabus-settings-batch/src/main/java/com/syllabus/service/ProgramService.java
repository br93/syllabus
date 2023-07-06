package com.syllabus.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.syllabus.helper.CSVHelper;
import com.syllabus.model.ProgramModel;
import com.syllabus.repository.ProgramRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProgramService {
	
	private final ProgramRepository programRepository;
	private final CSVHelper csvHelper;

	public void save(MultipartFile file){
		try {
			List<ProgramModel> programs = csvHelper.csvToProgram(file.getInputStream());
			programRepository.saveAll(programs);
		} catch (IOException ex) {
			throw new RuntimeException("fail to store csv data: " + ex.getMessage());
		}
	}
}

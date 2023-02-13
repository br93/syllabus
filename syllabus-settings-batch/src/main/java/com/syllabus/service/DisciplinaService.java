package com.syllabus.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.syllabus.helper.CSVHelper;
import com.syllabus.model.DisciplinaModel;
import com.syllabus.repository.DisciplinaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DisciplinaService {
	
	private final DisciplinaRepository disciplinaRepository;
	private final CSVHelper csvHelper;

	public void save(MultipartFile file){
		try {
			List<DisciplinaModel> disciplinas = csvHelper.csvToDisciplina(file.getInputStream());
			disciplinaRepository.saveAll(disciplinas);
		} catch (IOException ex) {
			throw new RuntimeException("fail to store csv data: " + ex.getMessage());
		}
	}
}

package com.syllabus.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.syllabus.helper.CSVHelper;
import com.syllabus.model.DisciplinaCursoModel;
import com.syllabus.repository.DisciplinaCursoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DisciplinaCursoService {
	
	private final DisciplinaCursoRepository disciplinaCursoRepository;
	private final CSVHelper csvHelper;

	public void save(MultipartFile file){
		try {
			List<DisciplinaCursoModel> disciplinaCursos = csvHelper.csvToDisciplinaCurso(file.getInputStream());
			saveFromList(disciplinaCursos);
		} catch (IOException ex) {
			throw new RuntimeException("fail to store csv data: " + ex.getMessage());
		}
	}
	
	private void saveFromList(List<DisciplinaCursoModel> list) {
		Integer size = list.size();
		
		for (int i = 0; i < size; i++) {
			disciplinaCursoRepository.save(list.get(i));
		}
	}
}

package com.syllabus.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.syllabus.helper.CSVHelper;
import com.syllabus.model.CursoModel;
import com.syllabus.repository.CursoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CursoService {
	
	private final CursoRepository cursoRepository;
	private final CSVHelper csvHelper;

	public void save(MultipartFile file){
		try {
			List<CursoModel> cursos = csvHelper.csvToCurso(file.getInputStream());
			cursoRepository.saveAll(cursos);
		} catch (IOException ex) {
			throw new RuntimeException("fail to store csv data: " + ex.getMessage());
		}
	}
}
